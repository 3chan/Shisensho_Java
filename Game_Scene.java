import java.util.*;

class Game_Scene extends asd.Scene {
	PieceObject obj;
	PieceObject[] obj_pieces;
	FigureObject[] obj_figures;
	NewGameObject obj_newGame;
	UndoAllObject obj_undoAll;
	UndoOnceObject obj_undoOnce;
	PauseGameObject obj_pauseGame;
	asd.Layer2D layer;
	boolean cleared;
	History his;
	GameTimer gtimer;
	boolean pauseStart;
	boolean isPause;
	boolean pauseEnd;

	protected void OnRegistered() {
		// レイヤーを作り、シーンにレイヤーを追加する
		layer = new asd.Layer2D();
		AddLayer(layer);

		// 背景画像を読み込み (略) レイヤーにオブジェクトを追加する。
		obj = new PieceObject();
		asd.Texture2D tex = asd.Engine.getGraphics().CreateTexture2D("res\\game.png");
		obj.setTexture(tex);
		layer.AddObject(obj);

		// 駒の画像を読み込み、144個のオブジェクトに設定する。
		obj_pieces = new PieceObject[144];
		asd.Texture2D tex_pieces = asd.Engine.getGraphics().CreateTexture2D("res\\pieces.png");

		// 駒の位置を初期化し、テクスチャを登録する
		for (int i = 0; i < 144; i++) {
			obj_pieces[i] = new PieceObject();
			obj_pieces[i].setPiecePosition(i);
			obj_pieces[i].setTexture(tex_pieces);
			obj_pieces[i].setPieceTexture(i / 4);
		}

		// 駒の描画位置をランダムにする
		for (int i = 0; i < 800; i++) {
			ShufflePiecePosition();
		}

		// レイヤーにオブジェクトを追加する。
		for (int i = 0; i < 144; i++) {
			layer.AddObject(obj_pieces[i]);
		}

		// タイマー用の数字を読み込み、5個のオブジェクトに設定する
		obj_figures = new FigureObject[5];
		asd.Texture2D tex_figures = asd.Engine.getGraphics().CreateTexture2D("res\\number_black.png");

		// 数字とコロンのテクスチャを初期化する
		for (int i = 0; i < 5; i++) {
			obj_figures[i] = new FigureObject();
			obj_figures[i].setTexture(tex_figures);
			if (i == 2)
				obj_figures[i].init4timer(10, i);
			else
				obj_figures[i].init4timer(0, i);
		}

		// レイヤーにオブジェクトを追加する。
		for (int i = 0; i < 5; i++) {
			layer.AddObject(obj_figures[i]);
		}

		// アイコンの画像を読み込む
		asd.Texture2D tex_icons = asd.Engine.getGraphics().CreateTexture2D("res\\icon_black.png");

		// アイコンごとにテクスチャと位置を初期化する
		// NewGame
		obj_newGame = new NewGameObject();
		obj_newGame.setTexture(tex_icons);
		obj_newGame.init(0, 0);
		layer.AddObject(obj_newGame);
		// UndoAll
		obj_undoAll = new UndoAllObject();
		obj_undoAll.setTexture(tex_icons);
		obj_undoAll.init(1, 1);
		layer.AddObject(obj_undoAll);
		// UndoOnce
		obj_undoOnce = new UndoOnceObject();
		obj_undoOnce.setTexture(tex_icons);
		obj_undoOnce.init(2, 2);
		layer.AddObject(obj_undoOnce);
		// Pause/Play
		obj_pauseGame = new PauseGameObject();
		obj_pauseGame.setTexture(tex_icons);
		obj_pauseGame.init(3, 3);
		layer.AddObject(obj_pauseGame);
		// Hint

		// Undo用に駒の情報を保存
		his = new History();
		his.setHisAll(obj_pieces);

		// タイマーをセットする
		gtimer = new GameTimer();
		gtimer.setStartTime();
	}

	protected void OnUpdated() {
		// ポーズボタンがクリックされた時
		if (pauseEnd) {
			System.out.println("pauseEnd");
			isPause = false;
			pauseEnd = false;
			gtimer.setIsPause(false);
			// 画面の色を戻す
			obj.setColor(new asd.Color(255, 255, 255));
			// アイコンを戻す
			obj_pauseGame.setPauseTexture(3);
			return;
		} else if (pauseStart) {
			System.out.println("pauseStart");
			pauseStart = false;
			isPause = true;
			gtimer.setIsPause(true);
			// アイコンを設定する
			obj_pauseGame.setPauseTexture(5);
			return;
		} else if (isPause) {
			// 画面の色を設定する
			obj.setColor(new asd.Color(100, 100, 100));
			return;
		}

		// 通常はタイマーを更新する
		else {
			System.out.println(gtimer.getTime() / 1000);
			DrawTimer(gtimer.getTime() / 1000);
		}

		// チートモード
		if (asd.Engine.getMouse().getRightButton().getButtonState() == asd.MouseButtonState.Push) {
			System.out.println("チートモード (駒が全て残っている状態以外で発動すると落ちる)");
			for (int i = 0; i < 144; i++) {
				layer.RemoveObject(obj_pieces[i]);
				obj_pieces[i] = null;
			}
		}

		// 成立しているペアが無いか走査する
		CheckPaired();

		// クリア時はタイムを記録し画面遷移する
		if (CheckClear()) {
			System.out.println("画面遷移します");
			if (!cleared) {
				asd.Engine.ChangeSceneWithTransition(new Clear_Scene(gtimer.getTime() / 1000),
						new asd.TransitionFade(0.5f, 0.2f));
				cleared = true;
			}
		}
	}

	void ShufflePiecePosition() {
		Random rnd = new Random();
		int rnd1 = rnd.nextInt(144);
		int rnd2 = rnd.nextInt(144);
		int buf = obj_pieces[rnd1].getPieceTexture();
		obj_pieces[rnd1].setPieceTexture(obj_pieces[rnd2].getPieceTexture());
		obj_pieces[rnd2].setPieceTexture(buf);
	}

	void CheckPaired() {
		int p1 = -1;
		int p2 = -1;
		boolean paired = false;

		// 選択中の駒を検出
		for (int i = 0; i < 144; i++) {
			if (obj_pieces[i] == null || !obj_pieces[i].isColored)
				continue;
			if (p1 == -1) {
				p1 = i;
				System.out.println("p1 = " + p1);
			} else {
				p2 = i;
				System.out.println("p2 = " + p2);
			}
		}

		// 選択なしまたは1駒のみ選択中の場合何もしない
		if (p1 == -1 || p2 == -1)
			return;

		// 2駒のテクスチャが異なる場合選択を解除する
		if (obj_pieces[p1].getPieceTexture() != obj_pieces[p2].getPieceTexture()) {
			obj_pieces[p1].setIsColored(false);
			obj_pieces[p2].setIsColored(false);
			return;
		}

		if (!paired)
			paired = CheckLines(p1, p2, true);
		if (!paired)
			paired = CheckLines(p1, p2, false);

		// ここで選択解除しないと UndoAll した際に CheckPaired へ戻ってきてしまう
		obj_pieces[p1].setIsColored(false);
		obj_pieces[p2].setIsColored(false);

		if (paired) {
			System.out.println("History に登録します");
			his.setHis(obj_pieces[p1], obj_pieces[p2]);
			System.out.println("駒を消します");
			layer.RemoveObject(obj_pieces[p1]);
			layer.RemoveObject(obj_pieces[p2]);
			obj_pieces[p1] = null;
			obj_pieces[p2] = null;
			System.out.println("駒を消しました");
		}
	}

	// xまたはy座標が等しい2点間に駒があるか調べる
	boolean CheckLine(int p1, int p2, int start, int end, boolean isX) { // p1 < p2
		int add;

		// 走査方向を決める
		if (isX) {
			add = 1;
		} else {
			add = 12;
		}

		// p1 < p2 とする
		if (p2 < p1) {
			int buff = p1;
			p1 = p2;
			p2 = buff;
		}

		//if (p1 == p2) return true;

		for (int i = p1 /* + add */; i <= p2; i += add) {
			// 現在調べている position が空である場合
			if (obj_pieces[i] == null || i == start || i == end) {
				System.out.println("空");
				continue;
			}
			// 現在調べている position に他の駒がある場合
			else {
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
			side1 = p1 - p1 % 12;
			side2 = p2 - p2 % 12;
		} else {
			add = 1;
			addLine = 12;
			side1 = p1 % 12;
			side2 = p2 % 12;
		}

		// 12x12 外
		System.out.println("12x12 外");
		System.out.println("CheckLine(side1, p1, p1, p2, isX) = " + CheckLine(side1, p1, p1, p2, isX));
		System.out.println("CheckLine(side2, p2, p1, p2, isX) = " + CheckLine(side2, p2, p1, p2, isX));
		System.out.println(
				"CheckLine(side1 + 11, p1, p1, p2, isX) = " + CheckLine(side1 + addLine * 11, p1, p1, p2, isX));
		System.out.println(
				"CheckLine(side2 + 11, p2, p1, p2, isX) = " + CheckLine(side2 + addLine * 11, p2, p1, p2, isX));
		if ((CheckLine(side1, p1, p1, p2, isX) && CheckLine(side2, p2, p1, p2, isX))
				|| CheckLine(side1 + addLine * 11, p1, p1, p2, isX)
						&& CheckLine(side2 + addLine * 11, p2, p1, p2, isX)) {
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
				if (CheckLine(side1, p1, p1, p2, isX) && CheckLine(side2, p2, p1, p2, isX))
					return true;
			}
			side1 += addLine;
			side2 += addLine;
		}

		return false;
	}

	boolean CheckClear() {
		for (int i = 0; i < 144; i++) {
			if (obj_pieces[i] != null)
				return false;
		}
		return true;
	}

	// タイマーを描画する
	void DrawTimer(int nowTime) {
		int num = 0;

		System.out.println("DrawTimer");
		for (int i = 0; i < 5; i++) { // 01234 の順に 分分：秒秒
			// コロンは飛ばす
			if (i == 2)
				continue;

			// 分または秒を取得
			if (i < 2)
				num = nowTime / 60;
			else if (2 < i)
				num = nowTime % 60;

			// 描画
			if (i == 0 || i == 3) { // 10の位だったら
				System.out.println("Drawing: " + num / 10 + " at " + i);
				obj_figures[i].setFigureTexture(num / 10);
			} else { // 1の位だったら
				System.out.println("Drawing: " + num % 10 + " at " + i);
				obj_figures[i].setFigureTexture(num % 10);
			}
		}
	}
}