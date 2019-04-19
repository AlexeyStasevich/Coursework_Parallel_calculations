package Main;

public class SerialSolve {

	private int TPointsQuantity;
	private int HPointsQuantity;
	private double x0;
	private double h;
	private double t0;
	private double tau;
	private Diffur diffur;

	public SerialSolve(Diffur diffur) {
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
			for (int j = 1; j < HPointsQuantity - 1; j++) {
				w[i][j] = diffur.calculateApproximateSolution(w[i-1][j-1],w[i-1][j],w[i-1][j+1]);
			}
			w[i][HPointsQuantity - 1] = diffur.calculateRightBorder(t);
		}
		return w;
	}
}
