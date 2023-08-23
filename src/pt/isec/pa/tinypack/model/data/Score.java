package pt.isec.pa.tinypack.model.data;

import java.io.Serializable;

public class Score implements Serializable {



    private  String nome;
    private int score;


    public Score(String name, int valor){
        nome = name;
        score = valor;
    }

    public String getNome() {
        return nome;
    }

    public int getScore() {
        return score;
    }


}
