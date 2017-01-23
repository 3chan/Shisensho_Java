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
			if (obj_pieces[i] == null || !obj_pieces[i].isColored) continue;
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
		else
		{
			// 2駒間に1角以上ある場合を調べる
			paired = CheckLines(p1, p2);
		}

		if (paired) {
			System.out.println("駒を消します");
			layer.RemoveObject(obj_pieces[p1]);
			layer.RemoveObject(obj_pieces[p2]);
			obj_pieces[p1] = null;
			obj_pieces[p2] = null;
			System.out.println("駒を消しました");
		}
		else {
			obj_pieces[p1].setIsColored(false);
			obj_pieces[p2].setIsColored(false);
		}
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
				System.out.println("空");
				continue;
			}
			// 現在調べている position に他の駒がある場合
			else
			{
				System.out.println("使用中");
				return false;
			}
		}

		// 到達しないはず
		return false;
	}

	boolean CheckLines(int p1, int p2) {
		boolean slash;  // 2駒が対角に位置する長方形を作るとき、2駒の配置が右上-左下であるか
		int point0, point1, point2, point3;  // 左上, 右上, 左下, 右下

		if (p2%12 < p1%12) slash = true;
		else slash = false;

		if (slash) {
			point0 = p1 - (p1%12 - p2%12);
			point1 = p1;
			point2 = p2;
			point3 = p2 + (p1%12 - p2%12);
		}
		else {
			point0 = p1;
			point1 = p1 - (p1%12 - p2%12);
			point2 = p2 + (p1%12 - p2%12);
			point3 = p2;
		}

		int l = point0;
		int r = point1;
		for (int i = point0; i <= point3; i += 12) {
			if (CheckLine(l, r, true)) {
				if (slash) {
					if (CheckLine(point1, r, false) && CheckLine(l, point2, false)) return true;
				}
				else {
					if (CheckLine(point0, l, false) && CheckLine(r, point3, false)) return true;
				}
			}
			l += 12;
			r += 12;
		}

		int a = point0;
		int b = point2;
		for (int i = point0; i <= point1; i++) {
			if (CheckLine(a, b, false)) {
				if (slash) {
					if (CheckLine(a, point1, true) && CheckLine(point2, b, true)) return true;
				}
				else {
					if (CheckLine(point0, a, true) && CheckLine(b, point3, true)) return true;
				}
			}
			a++;
			b++;
		}

		return false;
	}
}