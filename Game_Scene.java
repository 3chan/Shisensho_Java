import java.util.*;

class Game_Scene extends asd.Scene
{
	PieceObject[] obj_pieces;
	FigureObject[] obj_figures;
	asd.Layer2D layer;
	boolean cleared = false;
	GameTimer gtimer;
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
		for (int i=0; i<800; i++)
		{
			ShufflePiecePosition();
		}


		// シーンにレイヤーを追加し、そのレイヤーにオブジェクトを追加する。
		for (int i=0; i<144; i++)
		{
			layer.AddObject(obj_pieces[i]);
		}

		// タイマー用の数字を読み込み、11個のオブジェクトに設定する
		obj_figures = new FigureObject[11];
		asd.Texture2D tex_figures = asd.Engine.getGraphics().CreateTexture2D("res\\number.png");

		// 数字のテクスチャを登録する
		for (int i=0; i<11; i++) {
			obj_figures[i] = new FigureObject();
			obj_figures[i].setTexture(tex_figures);
			obj_figures[i].setFigureTexture(i);
		}

		// シーンにレイヤーを追加し、そのレイヤーにオブジェクトを追加する。
		for (int i=0; i<11; i++) {
			layer.AddObject(obj_figures[i]);
		}

		// タイマーをセットする
		gtimer = new GameTimer();
		gtimer.setStartTime();
	}

	protected void OnUpdated()
	{
		// タイマーを更新する
		System.out.println(gtimer.getTime() / 1000);
		DrawTimer(gtimer.getTime() / 1000);

		// チートモード
		if (asd.Engine.getMouse().getRightButton().getButtonState() == asd.MouseButtonState.Push) {
			System.out.println("チートモード");
			for (int i=0; i < obj_pieces.length; i++) {
				layer.RemoveObject(obj_pieces[i]);
				obj_pieces[i] = null;
			}
		}

		// 成立しているペアが無いか走査する
		CheckPaired();

		// クリア時は画面遷移する
		if (CheckClear()) {
			System.out.println("画面遷移します");
			if (cleared == false) {
				asd.Engine.ChangeSceneWithTransition(new Clear_Scene(), new asd.TransitionFade(1.0f, 1.5f));
				cleared = true;
			}
		}
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
		
		// 選択中の駒を検出
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
		if (obj_pieces[p1].getPieceTexture() != obj_pieces[p2].getPieceTexture())
		{
			obj_pieces[p1].setIsColored(false);
			obj_pieces[p2].setIsColored(false);
			return;
		}

		if (!paired) paired = CheckLines(p1, p2, true);
		if (!paired) paired = CheckLines(p1, p2, false);

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
	boolean CheckLine(int p1, int p2, int start, int end, boolean isX) {  // p1 < p2
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

		// p1 < p2 とする
		if (p2 < p1) {
			int buff = p1;
			p1 = p2;
			p2 = buff;
		}

		//if (p1 == p2) return true;

		for (int i = p1 /* + add */; i <= p2; i += add)
		{
			// 現在調べている position が空である場合
			if (obj_pieces[i] == null || i == start || i == end)
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

		return true;
	}

	boolean CheckLines(int p1, int p2, boolean isX) {
		int add, addLine;
		int side1, side2;

		// 走査方向を決める
		if (isX) {
			add = 12;
			addLine = 1;
			side1 = p1 - p1%12;
			side2 = p2 - p2%12;
		}
		else {
			add = 1;
			addLine = 12;
			side1 = p1%12;
			side2 = p2%12;
		}

		// 12x12 外
		System.out.println("12x12 外");
		System.out.println("CheckLine(side1, p1, p1, p2, isX) = " + CheckLine(side1, p1, p1, p2, isX));
		System.out.println("CheckLine(side2, p2, p1, p2, isX) = " + CheckLine(side2, p2, p1, p2, isX));
		System.out.println("CheckLine(side1 + 11, p1, p1, p2, isX) = "+ CheckLine(side1 + addLine*11, p1, p1, p2, isX));
		System.out.println("CheckLine(side2 + 11, p2, p1, p2, isX) = "+ CheckLine(side2 + addLine*11, p2, p1, p2, isX));
		if ((CheckLine(side1, p1, p1, p2, isX) && CheckLine(side2, p2, p1, p2, isX)) || 
			 CheckLine(side1 + addLine*11, p1, p1, p2, isX) && CheckLine(side2 + addLine*11, p2, p1, p2, isX)) {
			return true;
		}

		// 12x12 内
		System.out.println("12x12 内");
		for (int i = 0; i < 12; i++) {
			System.out.println("CheckLine(side1, side2, p1, p2, !isX) = " + CheckLine(side1, side2, p1, p2, !isX));
			System.out.println("side1 = " + side1);
			System.out.println("side2 = " + side2);
			if (CheckLine(side1, side2, p1, p2, !isX)) {
				System.out.println("CheckLine(side1, p1, p1, p2, isX) = " + CheckLine(side1, p1, p1, p2, isX));
				System.out.println("CheckLine(side2, p2, p1, p2, isX) = " + CheckLine(side2, p2, p1, p2, isX));
				if (CheckLine(side1, p1, p1, p2, isX) && CheckLine(side2, p2, p1, p2, isX)) return true;
			}
			side1 += addLine;
			side2 += addLine;
		}

		return false;
	}

	boolean CheckClear() {
		for (int i = 0; i < 144; i++) {
			if (obj_pieces[i] != null) return false;
		}
		return true;
	}

	// タイマーを描画する
	void DrawTimer(int nowTime) {
		int d = (int)Math.pow(10, 3);
		int num;

		// DEBUG
		for (int j = 0 ; j < 4; j++) {
			for (int k = 0; k < 11; k++) {
				System.out.print(obj_figures[k].getIsPlaced(j) + " ");
			}
			System.out.println();
		}

		System.out.println("DrawTimer");
		for (int i = 0; i < 4; i++) {
			num = nowTime / d;
			// if (!obj_figures[num].getIsPlaced(i)) {
				System.out.println("num = " + num);
				System.out.println("Drawing: " + num + " at " + i);
				obj_figures[num].setPosition(new asd.Vector2DF(50 + i * 20, 50 + 12 * 40 + 10));
				obj_figures[num].setIsPlaced(i, true);

			// 	if (num == 0) {
			// 		obj_figures[9].setIsPlaced(i, false);
			// 	}
			// 	else {
			// 		obj_figures[num - 1].setIsPlaced(i, false);
			// 	}
			// }
			nowTime %= d;
			d /= 10;
		}
 	}
}