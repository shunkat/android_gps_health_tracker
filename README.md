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

結果的にディレクトリ構成は以下のようになる
```
/root

```

# ユースケース


## メモ
- androidViewModelではなくそれの継承元のviewModelクラスを使うようにしよう

### テストする項目
推奨アーキテクチャによると必須とされているもの
- フローを含むViewModelの単体テスト
- データソースとレポジトリの単体テスト
- UIテスト

- （この下はわからない）
- StateFlow？
- モックよりフェイク優先？
