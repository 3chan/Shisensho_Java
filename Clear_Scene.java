import java.lang.reflect.Method;
import java.util.*;

class Clear_Scene extends asd.Scene
{
	int focus;
	boolean enter;
	asd.Layer2D layer;
	asd.TextureObject2D obj;
	AlphabetObject[] obj_alphabets;
	ArrayList<asd.Keys> keys;

    protected void OnRegistered() {
		// ���C���[�����V�[���Ƀ��C���[��ǉ�����
		asd.Layer2D layer = new asd.Layer2D();
		AddLayer(layer);

        // �w�i�摜��ǂݍ��݁A���C���[�ɃI�u�W�F�N�g��ǉ�����B
		asd.TextureObject2D obj = new asd.TextureObject2D();
		asd.Texture2D tex = asd.Engine.getGraphics().CreateTexture2D("res\\clear.png");
		obj.setTexture(tex);
		layer.AddObject(obj);

		// �A���t�@�x�b�g�̉摜��ǂݍ��݁A10�̃I�u�W�F�N�g�ɐݒ肷��
		obj_alphabets = new AlphabetObject[10];
		asd.Texture2D tex_alphabets = asd.Engine.getGraphics().CreateTexture2D("res\\alphabet_ed.png");

		// �A���t�@�x�b�g�̈ʒu�ƃe�N�X�`�������������A���C���[�ɃI�u�W�F�N�g��ǉ�����
		for (int i=0; i<10; i++) {
			obj_alphabets[i] = new AlphabetObject();
			obj_alphabets[i].setTexture(tex_alphabets);  // �����ʒu���� (debug: A) �Ŏw�肵�Ă���
			obj_alphabets[i].init4clear(0, i);
			layer.AddObject(obj_alphabets[i]);
		}

		keys = new ArrayList<asd.Keys>();
		keys.add(asd.Keys.A);
		keys.add(asd.Keys.B);
		keys.add(asd.Keys.C);
		keys.add(asd.Keys.D);
		keys.add(asd.Keys.E);
		keys.add(asd.Keys.F);
		keys.add(asd.Keys.G);
		keys.add(asd.Keys.H);
		keys.add(asd.Keys.I);
		keys.add(asd.Keys.J);
		keys.add(asd.Keys.K);
		keys.add(asd.Keys.L);
		keys.add(asd.Keys.M);
		keys.add(asd.Keys.N);
		keys.add(asd.Keys.O);
		keys.add(asd.Keys.P);
		keys.add(asd.Keys.Q);
		keys.add(asd.Keys.R);
		keys.add(asd.Keys.S);
		keys.add(asd.Keys.T);
		keys.add(asd.Keys.U);
		keys.add(asd.Keys.V);
		keys.add(asd.Keys.W);
		keys.add(asd.Keys.X);
		keys.add(asd.Keys.Y);
		keys.add(asd.Keys.Z);
		keys.add(asd.Keys.Space);
		keys.add(asd.Keys.Backspace);
		keys.add(asd.Keys.Left);
		keys.add(asd.Keys.Right);
		keys.add(asd.Keys.Enter);		
	}

	protected void OnUpdated() {
		// �L�[���͂��󂯎��
		for (int i = 0; i < keys.size(); i++) {
			asd.KeyState state = asd.Engine.getKeyboard().GetKeyState(keys.get(i));
			if (state != asd.KeyState.Push) continue;

			// BackSpace
			if (i == 27) {
				if ((0 < focus && focus < obj_alphabets.length - 1) || (focus == obj_alphabets.length - 1 && obj_alphabets[obj_alphabets.length - 1].getAlphabetTexture() == 26)) focus --;
				obj_alphabets[focus].setAlphabetTexture(26);
				break;
			}

			// Left
			if (i == 28) {
				if (0 < focus) focus --;
				break;
			}

			// Right
			if (i == 29) {
				if (focus < obj_alphabets.length - 1) focus++;
				System.out.println("obj_alphabets.length = " + obj_alphabets.length);
				break;
			}

			// Enter
			if (i == 30) {
				// �O���t�@�C���ɓ��͂��ꂽ�������ۑ�

				// 1�b�����ăt�F�[�h�A�E�g���A1.5�b�����ăt�F�[�h�C���B
				if (enter == false) {
					asd.Engine.ChangeSceneWithTransition(new Title_Scene(), new asd.TransitionFade(1.0f, 1.5f));
					enter = true;
				}
				break;
			}

			// A-Z
			obj_alphabets[focus].setAlphabetTexture(i);
			if (focus < obj_alphabets.length - 1) focus++;
		}
    }
}