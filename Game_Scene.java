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

		// 駒の位置を初期化し、テクスチャを登録する
		for (int i=0; i<144; i++)
		{
			obj_pieces[i] = new PieceObject();
			obj_pieces[i].setPiecePosition(i);
			obj_pieces[i].setTexture(tex_pieces);
			obj_pieces[i].setPieceTexture(i/4);
		}

		// 駒の描画位置をランダムにする
		for (int i=0; i<1000; i++)
		{
			ShufflePiecePosition();
		}

		for (int i=0; i<144; i++)
		{
			// シーンにレイヤーを追加し、そのレイヤーにオブジェクトを追加する。
			layer.AddObject(obj_pieces[i]);
		}
	}

	protected void OnUpdated()
	{
		CheckPaired();
		// マウスの左ボタンが押されるのを待つ。
		// フェードアウト・インによるシーン遷移を開始する。
		// 1秒かけてフェードアウトし、1.5秒かけてフェードイン。
		//asd.Engine.ChangeSceneWithTransition(new Clear_Scene(), new asd.TransitionFade(1.0f, 1.5f));
    }

	void ShufflePiecePosition()
	{
		Random rnd = new Random();
		int rnd1 = rnd.nextInt(144);
		int rnd2 = rnd.nextInt(144);
		int buf = obj_pieces[rnd1].getPieceTexture();
		obj_pieces[rnd1].setPieceTexture(obj_pieces[rnd2].getPieceTexture());
		obj_pieces[rnd2].setPieceTexture(buf);
	}

	void CheckPaired()
	{
		int p1 = -1;
		int p2 = -1;
		boolean paired = false;
		
		// 選択中の駒を走査
		for (int i=0; i<144; i++) {
			if (!obj_pieces[i].isColored) continue;
			if (p1 == -1)
			{
				p1 = i;
				System.out.println("p1 = " + p1);
			}
			else
			{
				p2 = i;
				System.out.println("p2 = " + p2);
			}
		}
		
		// 選択なしまたは1駒のみ選択中の場合何もしない
		if (p1 == -1 || p2 == -1)
		{
			return;
		}
		// 2駒のテクスチャが異なる場合選択を解除する
		else if (obj_pieces[p1].getPieceTexture() != obj_pieces[p2].getPieceTexture())
		{
			obj_pieces[p1].setIsColored(false);
			obj_pieces[p2].setIsColored(false);
			return;
		}
			
		if (p1/12 == p2/12)
		{
			// y座標が同じ場合 2駒間がクリアか"行"を調べる
			System.out.println("y座標が同じ場合");
			paired = CheckLine(p1, p2, true);
		}
		else if (p1%12 == p2%12)
		{
			// x座標が同じ場合 2駒間がクリアか"列"を調べる
			System.out.println("x座標が同じ場合");
			paired = CheckLine(p1, p2, false);
		}

		/*
		else if 　// 2駒のx座標間がクリアな"行"があるか調べる
		{
		}
		else if 　// 2駒のy座標間がクリアな"列"があるか調べる
		{
		}
		*/

		if (paired) {
			System.out.println("駒を消します");
			layer.RemoveObject(obj_pieces[p1]);
			layer.RemoveObject(obj_pieces[p2]);
			System.out.println("駒を消しました");
		}
		obj_pieces[p1].setIsColored(false);
		obj_pieces[p2].setIsColored(false);
	}
	
	// xまたはy座標が等しい2点間に駒があるか調べる
	boolean CheckLine(int p1, int p2, boolean isX) {  // p1 < p2
		int add;
		// 走査方向を決める
		if (isX)
		{
			add = 1;
		}
		else
		{
			add = 12;
		}

		for (int i = p1 + add; i <= p2; i += add)
		{
			// 2つの駒が隣り合っていた場合
			if (i == p2)
			{
				return true;
			}
			// 現在調べている position が空である場合
			else if (obj_pieces[i] == null)
			{
				continue;
			}
			// 現在調べている position に他の駒がある場合
			else
			{
				return false;
			}
		}

		// 到達しないはず
		return false;
	}
}