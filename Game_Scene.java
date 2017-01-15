import java.util.*;

class Game_Scene extends asd.Scene
{
	asd.TextureObject2D[] obj_pieces;
	asd.Layer2D layer;
    protected void OnRegistered()
    {
		// レイヤーを作る。
 		layer = new asd.Layer2D();
		AddLayer(layer);
 
        // 背景画像を読み込み (略) レイヤーにオブジェクトを追加する。
		asd.TextureObject2D obj = new asd.TextureObject2D();
		asd.Texture2D tex = asd.Engine.getGraphics().CreateTexture2D("res\\game.png");
		obj.setTexture(tex);
		layer.AddObject(obj);

		// 駒の画像を読み込み、144個のオブジェクトに設定する。
		obj_pieces = new asd.TextureObject2D[144];
		asd.Texture2D tex_pieces = asd.Engine.getGraphics().CreateTexture2D("res\\pieces.png");

		// 駒の描画位置をランダムにする
		Random rnd = new Random();
		Integer[] rnd_position = new Integer[144];
		for (int i=0; i<144; i++) {
			rnd_position[i] = i;
		}
		for(int i=0; i<144; i++)
		{
			int int_rnd = rnd.nextInt(144);
			int buf = rnd_position[i];
			rnd_position[i] = rnd_position[int_rnd];
			rnd_position[int_rnd] = buf;
		}

		// 画像から駒一つ分を切り出して登録したのち、描画位置を設定する。
		for (int i=0; i<144; i++)
		{
			obj_pieces[i] = new PieceObject();
			obj_pieces[i].setTexture(tex_pieces);
			obj_pieces[i].setSrc(new asd.RectF((i/4)*40, 0, 40, 40));  // (x, y, 辺の長さ, 辺の長さ)
			System.out.println(rnd_position[i]);
			obj_pieces[i].setPosition(new asd.Vector2DF(50 + (rnd_position[i] % 12) * 40, 50 + (rnd_position[i] / 12) * 40));
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
}