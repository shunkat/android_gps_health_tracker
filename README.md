# 画面
1. ニックネーム登録画面
2. 情報入力画面（ダイアログ）
3. ホーム画面
4. 設定画面

# 機能
- 認証（匿名）
- ユーザー情報登録 / 更新
- GPS情報取得
    - バックグラウンド
    - 定期実行
    - Firestoreに送信
- Health ConnectAPIからのデータ取得
    - GPSと同様

# 設計（アーキテクチャ）
Google公式の推奨アーキテクチャを参考に作成した。
試行錯誤の歴史は[このスクラップ](https://zenn.dev/shun1997/scraps/90916eb0f048b4)にまとめてあります。

三つのレイヤーに分かれており、UI / Domain / Dataがある。
UIにはCompose関数と、ViewModelを配置。
Domainには、Usecaseの形でまとめたビジネスロジックを配置
Dataには、ローカルやFirestore、GPSデータ、Healthデータと直接やりとりするDatasourceと、それを操作しつつ、ビジネスロジックを記述するためのrepositoryを配置する。
推奨アーキテクチャと違うのは、ViewModelの直接ロジックを記述するのを避けて、DomainのUsecaseにロジックを集約した点。

また機能が少なく、共通利用部分が多くなることが予想できたので、layer firstなディレクトリ構成にしてある。

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

# ユースケース


## メモ
- androidViewModelではなくそれの継承元のviewModelクラスを使うようにしよう

### テストする項目
推奨アーキテクチャによると必須とされているもの
- フローを含むViewModelの単体テスト
- データソースとレポジトリの単体テスト
- UIテスト
