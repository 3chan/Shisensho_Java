import java.util.*;

class Game_Scene extends asd.Scene
{
	PieceObject[] obj_pieces;
	asd.Layer2D layer;
    protected void OnRegistered()
    {
		// レイヤーを作る。
 		layer = new asd.Layer2D();
		AddLayer(layer);
 
		// 背景画像を読み込み (略) レイヤーにオブジェクトを追加する。
		PieceObject obj = new PieceObject();
		asd.Texture2D tex = asd.Engine.getGraphics().CreateTexture2D("res\\game.png");
		obj.setTexture(tex);
		layer.AddObject(obj);

		// 駒の画像を読み込み、144個のオブジェクトに設定する。
		obj_pieces = new PieceObject[144];
		asd.Texture2D tex_pieces = asd.Engine.getGraphics().CreateTexture2D("res\\pieces.png");

		// 駒の位置を初期化する
		for (int i=0; i<144; i++) {
			obj_pieces[i] = new PieceObject();
			obj_pieces[i].setPiecePosition(i);
		}

		// 駒の描画位置をランダムにする
		for (int i=0; i<1000; i++) {
			ShufflePiecePosition(obj_pieces);
		}

		// 画像から駒一つ分を切り出して登録したのち、描画位置を設定する。
		for (int i=0; i<144; i++)
		{
			obj_pieces[i].setTexture(tex_pieces);
			obj_pieces[i].setSrc(new asd.RectF((i/4)*40, 0, 40, 40));  // (x, y, 辺の長さ, 辺の長さ)
			obj_pieces[i].setPieceTexture(i/4);
			System.out.println(obj_pieces[i].getPiecePosition());
			obj_pieces[i].setPosition(new asd.Vector2DF(50 + (obj_pieces[i].getPiecePosition() % 12) * 40, 50 + (obj_pieces[i].getPiecePosition() / 12) * 40));
			// シーンにレイヤーを追加し、そのレイヤーにオブジェクトを追加する。		
			layer.AddObject(obj_pieces[i]);
		}
	}

	protected void OnUpdated() {
		// マウスの左ボタンが押されるのを待つ。
		// フェードアウト・インによるシーン遷移を開始する。
		// 1秒かけてフェードアウトし、1.5秒かけてフェードイン。
		//asd.Engine.ChangeSceneWithTransition(new Clear_Scene(), new asd.TransitionFade(1.0f, 1.5f));
    }

	void ShufflePiecePosition(PieceObject[] pieces){
		Random rnd = new Random();
		int rnd1 = rnd.nextInt(144);
		int rnd2 = rnd.nextInt(144);
		int buf = obj_pieces[rnd1].getPiecePosition();
		obj_pieces[rnd1].setPiecePosition(obj_pieces[rnd2].getPiecePosition());
		obj_pieces[rnd2].setPiecePosition(buf);
	}
}