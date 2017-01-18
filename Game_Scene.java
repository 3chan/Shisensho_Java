import java.util.*;

class Game_Scene extends asd.Scene
{
	PieceObject[] obj_pieces;
	asd.Layer2D layer;
    protected void OnRegistered()
    {
		// ���C���[�����B
 		layer = new asd.Layer2D();
		AddLayer(layer);
 
		// �w�i�摜��ǂݍ��� (��) ���C���[�ɃI�u�W�F�N�g��ǉ�����B
		PieceObject obj = new PieceObject();
		asd.Texture2D tex = asd.Engine.getGraphics().CreateTexture2D("res\\game.png");
		obj.setTexture(tex);
		layer.AddObject(obj);

		// ��̉摜��ǂݍ��݁A144�̃I�u�W�F�N�g�ɐݒ肷��B
		obj_pieces = new PieceObject[144];
		asd.Texture2D tex_pieces = asd.Engine.getGraphics().CreateTexture2D("res\\pieces.png");

		// ��̈ʒu������������
		for (int i=0; i<144; i++) {
			obj_pieces[i] = new PieceObject();
			obj_pieces[i].setPiecePosition(i);
		}

		// ��̕`��ʒu�������_���ɂ���
		for (int i=0; i<1000; i++) {
			ShufflePiecePosition(obj_pieces);
		}

		// �摜���������؂�o���ēo�^�����̂��A�`��ʒu��ݒ肷��B
		for (int i=0; i<144; i++)
		{
			obj_pieces[i].setTexture(tex_pieces);
			obj_pieces[i].setSrc(new asd.RectF((i/4)*40, 0, 40, 40));  // (x, y, �ӂ̒���, �ӂ̒���)
			obj_pieces[i].setPieceTexture(i/4);
			System.out.println(obj_pieces[i].getPiecePosition());
			obj_pieces[i].setPosition(new asd.Vector2DF(50 + (obj_pieces[i].getPiecePosition() % 12) * 40, 50 + (obj_pieces[i].getPiecePosition() / 12) * 40));
			// �V�[���Ƀ��C���[��ǉ����A���̃��C���[�ɃI�u�W�F�N�g��ǉ�����B		
			layer.AddObject(obj_pieces[i]);
		}
	}

	protected void OnUpdated() {
		// �}�E�X�̍��{�^�����������̂�҂B
		// �t�F�[�h�A�E�g�E�C���ɂ��V�[���J�ڂ��J�n����B
		// 1�b�����ăt�F�[�h�A�E�g���A1.5�b�����ăt�F�[�h�C���B
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