import java.util.Arrays;

public class Dispatcher {
    public static double rightSolve(int n) {
        double h = 1.0 / (n - 1);
        double[] a = new double[n];
        double[] b = new double[n];
        double[] c = new double[n];
        double[] f = new double[n];
        double[] alpha = new double[n];
        double[] beta = new double[n];
        double[] y = new double[n];
        double[] x = new double[n];
        double[] yx = new double[n];

        Arrays.fill(y, 0);
        Arrays.fill(alpha, 0);
        Arrays.fill(beta, 0);
        Arrays.fill(x, 0);
        Arrays.fill(yx, 0);

        a[0] = 0;
        b[n - 1] = 0;
        c[0] = 1 + 2 / (h * h);
        c[n - 1] = 1 + 2 / (h * h);
        f[0] = 0;
        f[n - 1] = 0.5;

        for (int i = 0; i < n; i++) {
            if (i > 0 && i < n - 1) {
                a[i] = 1 / (h * h) + (1 + i * h) / (2 * h);
                b[i] = 1 / (h * h) - (1 + i * h) / (2 * h);
                c[i] = 1 + 2 / (h * h);
                f[i] = 2 / ((1 + h * i) * (1 + h * i) * (1 + h * i));
            }
        }

        alpha[0] = 0;
        beta[0] = 0;

        for (int i = 0; i < n - 1; i++) {
            double dilnyk = a[i] * alpha[i] + c[i];
            alpha[i + 1] = -b[i] / dilnyk;
            beta[i + 1] = (f[i] - a[i] * beta[i]) / dilnyk;
        }

        y[n - 1] = (f[n - 1] - a[n - 1] * beta[n - 1]) / (c[n - 1] + a[n - 1] * alpha[n - 1]);

        for (int i = n - 2; i >= 0; i--) {
            y[i] = alpha[i + 1] * y[i + 1] + beta[i + 1];
        }

        for (int i = 0; i < n; i++) {
            x[i] = i * h;
            yx[i] = (i == 0 || i == n - 1) ? y[i] : 1 / (x[i] + 1);
        }

        double yMinY = 0;

        for (int i = 0; i < n; i++) {
            yMinY += Math.pow(y[i] - yx[i], 2);
        }

        yMinY = Math.sqrt(yMinY);

        return yMinY;
    }

    public static void main(String[] args) {
        int n = 10;
        double yMinY = rightSolve(n);
        System.out.printf("n= %d:\n", n);
        System.out.printf("||y-y*|| = %.7f\n", yMinY);

//        System.out.println();
//
//        n = 100;
//        yMinY = rightSolve(n);
//        System.out.printf("n= %d:\n", n);
//        System.out.printf("||y-y*|| = %.7f\n", yMinY);
    }
}
