# はじめに
なぜ一人で開発してるのにコーディングガイドラインが必要なのか？
その理由は、「忘れてしまうからだ」

また、コーディングガイドラインと言いつつ、開発を進めるにあたって疑問に思う可能性があるものを全部突っ込んでいる。

## 記述時のルール
[Googleのスタイルガイド](https://developer.android.com/kotlin/style-guide?hl=ja)に準拠する

## gitブランチルール
- デフォルトブランチをmainとしたgit flowを使う
- トピックブランチはfeat/[変更を端的に表した短文]という命名にする
  - 例えば、認証機能追加ならfeat/add-authentication
- プルリクエストのマージ時に、topic→developはsquashし、develop→mainはrebaseする
  - 開発初期の0→1はPRの粒度が大きくなる。しかし、無理やり小さくするのは難しいので、topicブランチの子ブランチを作成して、子ブランチからtopicブランチ本体へのマージの時にsquashすることにした。

