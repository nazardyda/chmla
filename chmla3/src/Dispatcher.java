public class Dispatcher {
    public static void main(String[] args) {
        int n = 100; // Кількість рівнянь
        double h = 1.0 / n; // Крок
        double[] a = new double[n-1];
        double[] b = new double[n-1];
        double[] c = new double[n-1];
        double[] f = new double[n-1];
        double[] y = new double[n+1];

        // Обчислення коефіцієнтів
        for (int i = 0; i < n-1; i++) {
            double x = (i+1) * h;
            a[i] = (1.0 / (h*h)) + ((1.0 + x) / (2.0 * h));
            b[i] = (1.0 / (h*h)) - ((1.0 + x) / (2.0 * h));
            c[i] = 1.0 + (2.0 / (h*h));
            f[i] = 2.0 / Math.pow((1.0 + x), 3.0);
        }

        // Граничні умови
        y[0] = 1.0;
        y[n] = 0.5;

        // Прямий хід методу лівої прогонки
        double[] alpha = new double[n-1];
        double[] beta = new double[n-1];
        alpha[0] = -b[0] / c[0];
        beta[0] = f[0] / c[0];
        for (int i = 1; i < n-1; i++) {
            alpha[i] = -b[i] / (a[i-1] * alpha[i-1] + c[i]);
            beta[i] = (f[i] - a[i-1] * beta[i-1]) / (a[i-1] * alpha[i-1] + c[i]);
        }

        // Зворотній хід методу лівої прогонки
        y[n-1] = (f[n-2] - a[n-2] * beta[n-2]) / (c[n-2] + a[n-2] * alpha[n-2]);
        for (int i = n-2; i >= 0; i--) {
            y[i+1] = alpha[i] * y[i+2] + beta[i];
        }

        // Виведення розв'язку
        System.out.println("x \t y(x) \t y*(x)");
        for (int i = 0; i <= n; i++) {
            double x = i * h;
            System.out.printf("%.4f \t %.4f \t %.4f\n", x, y[i], 1.0 / (x+1.0));
        }
    }
}
