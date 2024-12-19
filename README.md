# 概要
このアプリは、認知症早期発見を目的とした研究プロジェクトの中で使用されたアプリケーションを
類似研究を行う方々が使えるように、 Androidアプリとして作り直して公開したものである。

自由に使っていただきたい。

バックエンドを自由に付け替えられるように レイヤーを分割し、DIの仕組みを内包している。
運用時に使いやすいように、Google推奨アーキテクチャに準拠した設計になっている。

1から実装するより実装コストを下げられるはずだ。
もし不明点があれば加藤個人のメールアドレスに連絡をいただければ、できる限り素早く返信するつもりなので、お気軽にどうぞ。

## どういうアプリか？
アプリがバックグラウンドで、GPS情報と健康情報を取得しバックエンドに送信する。
また、そのデータを元に作成された認知レベルに関するレポートを画面上に表示する。

# 画面
上記のアプリを実現するために必要な画面は以下の4つ。
1. ニックネーム登録画面
2. 情報入力画面（ダイアログ）
3. ホーム画面(定期レポート表示)
4. 設定画面

# 機能
このアプリに必要な機能は以下の4つ。
- 認証（匿名）
- ユーザー情報登録 / 更新
- GPS情報取得
    - バックグラウンド
    - 定期実行
    - Firestoreに送信
- Health ConnectAPIからのデータ取得
    - GPSと同様
- 定期レポート表示

# 設計（アーキテクチャ）
Google公式の推奨アーキテクチャを参考に作成した。
試行錯誤の歴史は[このスクラップ](https://zenn.dev/shun1997/scraps/90916eb0f048b4)にまとめてあります。

三つのレイヤーに分かれており、UI / Domain / Dataがある。
UIにはCompose関数と、ViewModelを配置。
Domainには、Usecaseの形でまとめたビジネスロジックを配置
Dataには、ローカルやFirestore、GPSデータ、Healthデータと直接やりとりするDatasourceと、それを操作しつつ、ビジネスロジックを記述するためのrepositoryを配置する。

またfeature firstにするべきかlayer firstにするべきかは悩ましかったが
自分にとってはlayer firstの方が馴染み深かったのでlayer firstを選択した。

結果的にディレクトリ構成は以下のようになる

testフォルダやandroidtestフォルダ、resフォルダはrootと同じ階層にあるが、複雑になるのでここでは省略
```
/root
├── ui
│   ├── home
│   │   ├── HomePage.kt
│   │   ├── components
│   │   └── HomeViewModel.kt
│   ├── setting
│   │   ├── SettingPage.kt
│   │   ├── components
│   │   └── SettingViewModel.kt
│   ├── auth
│   │   ├── AuthPage.kt
│   │   ├── components
│   │   └── AuthViewModel.kt
│   └── common
│       ├── theme
│       └── components
├── domain
│   └── usecase
│       ├── user
│       ├── gps
│       ├── health
│       └── auth
├── data
│   ├── repository
│   │   ├── GpsRepository.kt
│   │   ├── HealthRepository.kt
│   │   ├── ReportRepository.kt
│   │   └── UserRepository.kt
│   └── source
│       ├── gps
│       │   ├── Gps.kt
│       │   ├── GpsModelMappingExt.kt
│       │   ├── local
│       │   │   ├── LocalGps.kt
│       │   │   └── LocalGpsSource.kt
│       │   └── network
│       │       ├── NetworkGps.kt
│       │       └── NetworkGpsSource.kt
│       ├── health
│       │   ├── Health.kt
│       │   ├── HealthModelMappingExt.kt
│       │   ├── local
│       │   │   ├── LocalHealth.kt
│       │   │   └── LocalHealthSource.kt
│       │   └── network
│       │       ├── NetworkHealth.kt
│       │       └── NetworkHealthSource.kt
│       ├── report
│       │   ├── Report.kt
│       │   ├── ReportModelMappingExt.kt
│       │   ├── local
│       │   │   ├── LocalReport.kt
│       │   │   └── LocalReportSource.kt
│       │   └── network
│       │       ├── NetworkReport.kt
│       │       └── NetworkReportSource.kt
│       └── user
│           ├── User.kt
│           ├── UserModelMappingExt.kt
│           ├── local
│           │   ├── LocalUser.kt
│           │   └── LocalUserSource.kt
│           └── network
│               ├── NetworkUser.kt
│               └── NetworkUserSource.kt
├── di
│   ├── AppModule.kt
│   ├── RepositoryModule.kt
│   └── NetworkModule.kt
└── MainActivity.kt
```


## 環境
- Gradle JDK: 17.0.7
- Gradle: 8.2.0