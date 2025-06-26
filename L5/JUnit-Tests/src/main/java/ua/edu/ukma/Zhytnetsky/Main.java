package ua.edu.ukma.Zhytnetsky;

public final class Main {

    public static void main(String[] args) {
        final Expression<Integer> expr = new Expression<>(3, 2, MathUtils::add);
        System.out.println("Expression value is " + expr.evaluate());
        System.out.println("Expression string is " + expr.display());
    }

}
