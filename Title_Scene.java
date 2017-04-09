class Title_Scene extends asd.Scene {
	boolean flag;

	protected void OnRegistered() {
		// �摜��ǂݍ��݁A�I�u�W�F�N�g�ɐݒ肷��B
		asd.TextureObject2D obj = new asd.TextureObject2D();
		asd.Texture2D tex = asd.Engine.getGraphics().CreateTexture2D("res\\start.png");
		obj.setTexture(tex);

		// �V�[���Ƀ��C���[��ǉ����A���̃��C���[�ɃI�u�W�F�N�g��ǉ�����B
		asd.Layer2D layer = new asd.Layer2D();
		AddLayer(layer);
		layer.AddObject(obj);
	}

	protected void OnUpdated() {
		// �}�E�X�̍��{�^���܂��� Enter ���������̂�҂B
		if ((asd.Engine.getMouse().getLeftButton().getButtonState() == asd.MouseButtonState.Push)
				|| (asd.Engine.getKeyboard().GetKeyState(asd.Keys.Enter) == asd.KeyState.Push)) {
			// �t�F�[�h�A�E�g�E�C���ɂ��V�[���J�ڂ��J�n����B
			// 0.5�b�����ăt�F�[�h�A�E�g���A0.2�b�����ăt�F�[�h�C���B
			if (!flag) {
				asd.Engine.ChangeSceneWithTransition(new Game_Scene(), new asd.TransitionFade(0.5f, 0.2f));
				flag = true;
			}
		}
	}
}