class Main
{
    static
    {
       System.loadLibrary("Altseed_core");
    }
    public static void main(String[] args)
    {
        // Altseedを初期化する。
		asd.Engine.Initialize("Main", 580, 655, new asd.EngineOption());

		// シーンのインスタンスを生成する。
		Title_Scene scene = new Title_Scene();

		// シーンを"タイトル"に設定する。
		asd.Engine.ChangeScene(scene);

		// Altseedのウインドウが閉じられていないか確認する。
		while(asd.Engine.DoEvents())
		{
			// Altseedを更新する。
			asd.Engine.Update();
		}
		

		// Altseedを終了する。
		asd.Engine.Terminate();
    }
}