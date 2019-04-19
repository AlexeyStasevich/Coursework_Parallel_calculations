package Main;

public class Diffur {

	private final double x0 = 0;
	private final double x1 = 1;
	private final double h = 1.0/30;
	private final double t0 = 0;
	private final double t1 = 1;
	private final double tau = 1.0/900;
	private final double a = 0.00005;
	private final double b = 0.00005;
	private final double A = 1.0;
	private final double B = 5.0;

	private double sigma = tau / (h * h);

	public double getX0() {
		return x0;
	}

	public double getX1() {
		return x1;
	}

	public double getH() {
		return h;
	}

	public double getT0() {
		return t0;
	}

	public double getT1() {
		return t1;
	}

	public double getTau() {
		return tau;
	}
	
	public int getTPointsQuantity() {
		return (int) Math.ceil((t1 - t0) / tau) + 1;
	}

	public int getHPointsQuantity() {
		return (int) Math.ceil((x1 - x0) / h) + 1;
	}
	
	public double calculateTrueSolution(double x, double t) {
		return (Math.pow(x, 2)/(B-6*a*t) + A*Math.pow(B-6*a*t, -1.0/3) - (b*(B-6*a*t))/(8*a));
	}

	public double calculateBottomBorder(double x) {
		return (Math.pow(x, 2)/B + A*Math.pow(B, -1.0/3) - (b*B)/(8*a));
	}

	public double calculateLeftBorder(double t) {
		return (A*Math.pow(B-6*a*t, -1.0/3) - (b*(B-6*a*t))/(8*a));
	}

	public double calculateRightBorder(double t) {
		return (1.0/(B-6*a*t) + A*Math.pow(B-6*a*t, -1.0/3) - (b*(B-6*a*t))/(8*a));
	}
	
	public double calculateApproximateSolution(double wLeft, double wCurrent , double wRight) {
		return (wCurrent + a*sigma*(0.25*Math.pow(wRight - wLeft, 2) + wCurrent*(wRight - wCurrent + wLeft))+b*tau );
	}

	public void printMatrix(double[][] matrix) {
		for (int i = 0; i < matrix.length; ++i) {
			for (int j = 0; j < matrix[i].length; ++j) {
				System.out.print(String.format("%.7f\t", matrix[i][j]));
			}
			System.out.println();
		}
	}

}
