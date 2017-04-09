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
		// ���C���[�����A�V�[���Ƀ��C���[��ǉ�����
		layer = new asd.Layer2D();
		AddLayer(layer);

		// �w�i�摜��ǂݍ��� (��) ���C���[�ɃI�u�W�F�N�g��ǉ�����B
		obj = new PieceObject();
		asd.Texture2D tex = asd.Engine.getGraphics().CreateTexture2D("res\\game.png");
		obj.setTexture(tex);
		layer.AddObject(obj);

		// ��̉摜��ǂݍ��݁A144�̃I�u�W�F�N�g�ɐݒ肷��B
		obj_pieces = new PieceObject[144];
		asd.Texture2D tex_pieces = asd.Engine.getGraphics().CreateTexture2D("res\\pieces.png");

		// ��̈ʒu�����������A�e�N�X�`����o�^����
		for (int i = 0; i < 144; i++) {
			obj_pieces[i] = new PieceObject();
			obj_pieces[i].setPiecePosition(i);
			obj_pieces[i].setTexture(tex_pieces);
			obj_pieces[i].setPieceTexture(i / 4);
		}

		// ��̕`��ʒu�������_���ɂ���
		for (int i = 0; i < 800; i++) {
			ShufflePiecePosition();
		}

		// ���C���[�ɃI�u�W�F�N�g��ǉ�����B
		for (int i = 0; i < 144; i++) {
			layer.AddObject(obj_pieces[i]);
		}

		// �^�C�}�[�p�̐�����ǂݍ��݁A5�̃I�u�W�F�N�g�ɐݒ肷��
		obj_figures = new FigureObject[5];
		asd.Texture2D tex_figures = asd.Engine.getGraphics().CreateTexture2D("res\\number_black.png");

		// �����ƃR�����̃e�N�X�`��������������
		for (int i = 0; i < 5; i++) {
			obj_figures[i] = new FigureObject();
			obj_figures[i].setTexture(tex_figures);
			if (i == 2)
				obj_figures[i].init4timer(10, i);
			else
				obj_figures[i].init4timer(0, i);
		}

		// ���C���[�ɃI�u�W�F�N�g��ǉ�����B
		for (int i = 0; i < 5; i++) {
			layer.AddObject(obj_figures[i]);
		}

		// �A�C�R���̉摜��ǂݍ���
		asd.Texture2D tex_icons = asd.Engine.getGraphics().CreateTexture2D("res\\icon_black.png");

		// �A�C�R�����ƂɃe�N�X�`���ƈʒu������������
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

		// Undo�p�ɋ�̏���ۑ�
		his = new History();
		his.setHisAll(obj_pieces);

		// �^�C�}�[���Z�b�g����
		gtimer = new GameTimer();
		gtimer.setStartTime();
	}

	protected void OnUpdated() {
		// �|�[�Y�{�^�����N���b�N���ꂽ��
		if (pauseEnd) {
			System.out.println("pauseEnd");
			isPause = false;
			pauseEnd = false;
			gtimer.setIsPause(false);
			// ��ʂ̐F��߂�
			obj.setColor(new asd.Color(255, 255, 255));
			// �A�C�R����߂�
			obj_pauseGame.setPauseTexture(3);
			return;
		} else if (pauseStart) {
			System.out.println("pauseStart");
			pauseStart = false;
			isPause = true;
			gtimer.setIsPause(true);
			// �A�C�R����ݒ肷��
			obj_pauseGame.setPauseTexture(5);
			return;
		} else if (isPause) {
			// ��ʂ̐F��ݒ肷��
			obj.setColor(new asd.Color(100, 100, 100));
			return;
		}

		// �ʏ�̓^�C�}�[���X�V����
		else {
			System.out.println(gtimer.getTime() / 1000);
			DrawTimer(gtimer.getTime() / 1000);
		}

		// �`�[�g���[�h
		if (asd.Engine.getMouse().getRightButton().getButtonState() == asd.MouseButtonState.Push) {
			System.out.println("�`�[�g���[�h (��S�Ďc���Ă����ԈȊO�Ŕ�������Ɨ�����)");
			for (int i = 0; i < 144; i++) {
				layer.RemoveObject(obj_pieces[i]);
				obj_pieces[i] = null;
			}
		}

		// �������Ă���y�A����������������
		CheckPaired();

		// �N���A���̓^�C�����L�^����ʑJ�ڂ���
		if (CheckClear()) {
			System.out.println("��ʑJ�ڂ��܂�");
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

		// �I�𒆂̋�����o
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

		// �I���Ȃ��܂���1��̂ݑI�𒆂̏ꍇ�������Ȃ�
		if (p1 == -1 || p2 == -1)
			return;

		// 2��̃e�N�X�`�����قȂ�ꍇ�I������������
		if (obj_pieces[p1].getPieceTexture() != obj_pieces[p2].getPieceTexture()) {
			obj_pieces[p1].setIsColored(false);
			obj_pieces[p2].setIsColored(false);
			return;
		}

		if (!paired)
			paired = CheckLines(p1, p2, true);
		if (!paired)
			paired = CheckLines(p1, p2, false);

		// �����őI���������Ȃ��� UndoAll �����ۂ� CheckPaired �֖߂��Ă��Ă��܂�
		obj_pieces[p1].setIsColored(false);
		obj_pieces[p2].setIsColored(false);

		if (paired) {
			System.out.println("History �ɓo�^���܂�");
			his.setHis(obj_pieces[p1], obj_pieces[p2]);
			System.out.println("��������܂�");
			layer.RemoveObject(obj_pieces[p1]);
			layer.RemoveObject(obj_pieces[p2]);
			obj_pieces[p1] = null;
			obj_pieces[p2] = null;
			System.out.println("��������܂���");
		}
	}

	// x�܂���y���W��������2�_�Ԃɋ���邩���ׂ�
	boolean CheckLine(int p1, int p2, int start, int end, boolean isX) { // p1 < p2
		int add;

		// �������������߂�
		if (isX) {
			add = 1;
		} else {
			add = 12;
		}

		// p1 < p2 �Ƃ���
		if (p2 < p1) {
			int buff = p1;
			p1 = p2;
			p2 = buff;
		}

		//if (p1 == p2) return true;

		for (int i = p1 /* + add */; i <= p2; i += add) {
			// ���ݒ��ׂĂ��� position ����ł���ꍇ
			if (obj_pieces[i] == null || i == start || i == end) {
				System.out.println("��");
				continue;
			}
			// ���ݒ��ׂĂ��� position �ɑ��̋����ꍇ
			else {
				System.out.println("�g�p��");
				return false;
			}
		}

		return true;
	}

	boolean CheckLines(int p1, int p2, boolean isX) {
		int add, addLine;
		int side1, side2;

		// �������������߂�
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

		// 12x12 �O
		System.out.println("12x12 �O");
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

		// 12x12 ��
		System.out.println("12x12 ��");
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

	// �^�C�}�[��`�悷��
	void DrawTimer(int nowTime) {
		int num = 0;

		System.out.println("DrawTimer");
		for (int i = 0; i < 5; i++) { // 01234 �̏��� �����F�b�b
			// �R�����͔�΂�
			if (i == 2)
				continue;

			// ���܂��͕b���擾
			if (i < 2)
				num = nowTime / 60;
			else if (2 < i)
				num = nowTime % 60;

			// �`��
			if (i == 0 || i == 3) { // 10�̈ʂ�������
				System.out.println("Drawing: " + num / 10 + " at " + i);
				obj_figures[i].setFigureTexture(num / 10);
			} else { // 1�̈ʂ�������
				System.out.println("Drawing: " + num % 10 + " at " + i);
				obj_figures[i].setFigureTexture(num % 10);
			}
		}
	}
}