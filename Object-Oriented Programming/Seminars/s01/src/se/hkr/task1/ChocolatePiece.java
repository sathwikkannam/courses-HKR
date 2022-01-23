package se.hkr.task1;

public class ChocolatePiece {
    private String shape;
    private int weight;
    private boolean eaten = false;

    public ChocolatePiece(String shape, int weight){
        setShape(shape);
        setWeight(weight);

    }

    public void setShape(String shape){
        this.shape = shape;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public void setEaten(boolean eaten){
        this.eaten = eaten;
    }

    public boolean isEaten(){
        return this.eaten;
    }

    public String getShape(){
        return this.shape;
    }

    public int getWeight(){
        return this.weight;
    }
}
