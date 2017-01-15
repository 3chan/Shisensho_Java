class Clear_Scene extends asd.Scene
{
    protected void OnRegistered()
    {
        // �摜��ǂݍ��݁A�I�u�W�F�N�g�ɐݒ肷��B
		asd.TextureObject2D obj = new asd.TextureObject2D();
		asd.Texture2D tex = asd.Engine.getGraphics().CreateTexture2D("res\\clear.png");
		obj.setTexture(tex);

		// �V�[���Ƀ��C���[��ǉ����A���̃��C���[�ɃI�u�W�F�N�g��ǉ�����B
		asd.Layer2D layer = new asd.Layer2D();
		AddLayer(layer);
		layer.AddObject(obj);
	}
	protected void OnUpdated() {
		// �}�E�X�̍��{�^�����������̂�҂B
		if((asd.Engine.getMouse().getLeftButton().getButtonState() == asd.MouseButtonState.Push))
		{
			// �t�F�[�h�A�E�g�E�C���ɂ��V�[���J�ڂ��J�n����B
			// 1�b�����ăt�F�[�h�A�E�g���A1.5�b�����ăt�F�[�h�C���B
			asd.Engine.ChangeSceneWithTransition(new Title_Scene(), new asd.TransitionFade(1.0f, 1.5f));
		}
    }
}