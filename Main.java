class Main
{
    static
    {
       System.loadLibrary("Altseed_core");
    }
    public static void main(String[] args)
    {
        // Altseed������������B
		asd.Engine.Initialize("Main", 580, 655, new asd.EngineOption());

		// �V�[���̃C���X�^���X�𐶐�����B
		Title_Scene scene = new Title_Scene();

		// �V�[����"�^�C�g��"�ɐݒ肷��B
		asd.Engine.ChangeScene(scene);

		// Altseed�̃E�C���h�E�������Ă��Ȃ����m�F����B
		while(asd.Engine.DoEvents())
		{
			// Altseed���X�V����B
			asd.Engine.Update();
		}
		

		// Altseed���I������B
		asd.Engine.Terminate();
    }
}