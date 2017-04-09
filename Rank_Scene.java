import java.util.*;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

class Rank_Scene extends asd.Scene {
    asd.Layer2D layer;
    asd.TextureObject2D obj;
    ArrayList<Score> score;
    FigureObject[][] time;
    AlphabetObject[][] name;
    String namebuf[];
    boolean change;

    protected void OnRegistered() {
        String str;
        boolean flag = false;
        String n;
        int t = 0;
        score = new ArrayList<Score>();

        // 外部ファイルからスコアを読み込む
        try {
            File file = new File("score.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((str = br.readLine()) != null) {
                if (!flag) {
                    t = Integer.parseInt(str);
                    flag = true;
                } else {
                    n = str;
                    flag = false;
                    score.add(new Score(t, n));
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

        // スコアをソートする
        Collections.sort(score, new ScoreComparator());

        // レイヤーを作りシーンにレイヤーを追加する
        asd.Layer2D layer = new asd.Layer2D();
        AddLayer(layer);

        // 背景画像を読み込み、レイヤーにオブジェクトを追加する。
        asd.TextureObject2D obj = new asd.TextureObject2D();
        asd.Texture2D tex = asd.Engine.getGraphics().CreateTexture2D("res\\ranking.png");
        obj.setTexture(tex);
        layer.AddObject(obj);

        // 数字を読み込み、5 x 5個のオブジェクトに設定する
        time = new FigureObject[5][5];
        asd.Texture2D tex_figures = asd.Engine.getGraphics().CreateTexture2D("res\\number_black.png");

        // アルファベットの画像を読み込み、5 x 8 個のオブジェクトに設定する
        name = new AlphabetObject[5][8];
        asd.Texture2D tex_alphabets = asd.Engine.getGraphics().CreateTexture2D("res\\alphabet_ed.png");

        // 数字およびアルファベットのテクスチャを設定する
        int[] numt = new int[5];
        for (int i = 0; i < 5; i++) {
            // スコアから上位タイムを1位から順に取得する
            // 記録されているスコアが5つ未満なら抜ける            
            if (score.size() <= i)
                break;

            // 数字とコロンのテクスチャを設定する
            numt = score.get(i).getTime();
            for (int j = 0; j < 5; j++) {
                System.out.println("numt[" + j + "] = " + numt[j]);
                time[i][j] = new FigureObject();
                time[i][j].setTexture(tex_figures);
                if (j == 2)
                    time[i][j].init4rank(10, j, i);
                else
                    time[i][j].init4rank(numt[j], j, i);
                // レイヤーにオブジェクトを追加する
                layer.AddObject(time[i][j]);
            }

            // アルファベットのテクスチャを設定する
            for (int j = 0; j < 8; j++) {
                name[i][j] = new AlphabetObject();
                name[i][j].setTexture(tex_alphabets);
                name[i][j].init4rank(score.get(i).getName(j), j, i);
                // レイヤーにオブジェクトを追加する
                layer.AddObject(name[i][j]);
            }
        }
    }

    protected void OnUpdated() {
        if ((asd.Engine.getKeyboard().GetKeyState(asd.Keys.Enter) == asd.KeyState.Push)
                || (asd.Engine.getMouse().getLeftButton().getButtonState() == asd.MouseButtonState.Push)) {
            if (!change) {
                asd.Engine.ChangeSceneWithTransition(new Title_Scene(), new asd.TransitionFade(0.5f, 0.2f));
                change = true;
            }
        }
    }
}