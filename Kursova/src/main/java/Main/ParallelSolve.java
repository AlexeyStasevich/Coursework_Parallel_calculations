package Main;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ParallelSolve {

	private int TPointsQuantity;
	private int HPointsQuantity;
	private double x0;
	private double h;
	private double t0;
	private double tau;
	private Diffur diffur;

	public ParallelSolve(Diffur diffur) {
		this.diffur = diffur;
		this.TPointsQuantity = diffur.getTPointsQuantity();
		this.HPointsQuantity = diffur.getHPointsQuantity();
		this.x0 = diffur.getX0();
		this.h = diffur.getH();
		this.t0 = diffur.getT0();
		this.tau = diffur.getTau();
	}

	public double[][] solve() {
		double t = t0 + tau;
		double x = x0;
		double w[][] = new double[TPointsQuantity][HPointsQuantity];
		for (int j = 0; j < HPointsQuantity; j++, x += h) {
			w[0][j] = diffur.calculateBottomBorder(x);
		}

		for (int i = 1; i < TPointsQuantity; ++i, t += tau) {
			w[i][0] = diffur.calculateLeftBorder(t);
			AtomicInteger ai = new AtomicInteger(i);			
			IntStream.range(1, diffur.getHPointsQuantity()-1).parallel().forEach(j -> {
			int m=ai.get();	
				w[m][j] = diffur.calculateApproximateSolution(w[m-1][j-1],w[m-1][j],w[m-1][j+1]);
			});
			w[i][HPointsQuantity - 1] = diffur.calculateRightBorder(t);
		}
		return w;
	}
}
