package pt.isec.pa.tinypack.model.data;

import java.io.*;
import java.util.*;

public class HighScores implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FILE_NAME = "./HighScores/highscores.ser";

    private Map<String, Integer> HighScores;

    public HighScores() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(FILE_NAME);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                HighScores hs = (HighScores) in.readObject();
                this.HighScores = hs.getHighScores();
                in.close();
                fileIn.close();
            } catch (IOException i) {
                i.printStackTrace();
            } catch (ClassNotFoundException c) {
                System.out.println("HighScores class not found");
                c.printStackTrace();
            }
        } else {
            this.HighScores = new HashMap<>();
        }
    }

    public void addScore(String nome,int  score) {
        //Map<String,Integer>  aux = new HashMap<>();
        Score aux;
        if(nome == null)
        {
            aux = new Score("Anonimo",score);

        }else{
            aux = new Score(nome,score);
        }


        HighScores.add(aux);
        /*HighScores.sort(new Comparator<Map<String, Integer>>() {
            @Override
            public int compare(Score m1, Score m2) {
                return m2.getScore().compareTo(m1.getScore());
            }
        });*/
        HighScores.sort(new Comparator<Score>() {
            @Override
            public int compare(Score m1, Score m2) {
                return Integer.compare(m2.getScore(), m1.getScore());
            }
        });
    }

    public Map<String, Integer> getHighScores() {
        return HighScores;
    }

    public Score getScoreAt(int index){
        if(index < 0 || index > HighScores.size() - 1)
        {
            return null;
        }

        return HighScores.get(index);
    }



    public List<Score> getTop5(){
        List<Score> aux = new ArrayList<>();

        for (int i = 0; i < 5; i++)
        {

            if(getScoreAt(i) == null)
            {
                aux.add(new Score("Empty", 0));
            }
            else {
                aux.add(getScoreAt(i));
            }
        }

        return aux;

    }

    public void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }


    public boolean IsTop5(int score){

        Score aux;

        if(HighScores.size() < 5) {
            return true;
        }

        for(int i = 0; i < 5; i++)
        {
            if(HighScores.get(i) == null)
            {
                return true;
            }

            aux = HighScores.get(i);
            if(score > aux.getScore())
            {
                return true;
            }


        }

        return false;
    }

    @Override
    public String toString() {
        return "HighScores{" +
                "scores=" + HighScores +
                '}';
    }
}