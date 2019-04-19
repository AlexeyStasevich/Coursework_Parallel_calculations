package Main;

public class Main {

	public static void main(String[] args) {
		System.out.println("ПОСЛЕДОВАТЕЛЬНОЕ РЕШЕНИЕ:");
		Diffur diffur = new Diffur();
		long startTimeSerial = System.nanoTime();
		double[][] serialSolve = new SerialSolve(diffur).solve();
		long endTimeSerial = System.nanoTime();
		long executeTimeSerial = endTimeSerial - startTimeSerial;
		System.out.println("Приближённый результат:");
		diffur.printMatrix(serialSolve);

		printResult(executeTimeSerial, diffur, serialSolve, calculateExactResult(diffur));
		
		System.out.println();
		System.out.println("\nПАРАЛЛЕЛЬНОЕ РЕШЕНИЕ:");
		long startTimeParallel = System.nanoTime();
		double[][] parallelSolve = new ParallelSolve(diffur).solve();
		long endTimeParallel = System.nanoTime();
		long executeTimeParallel = endTimeParallel - startTimeParallel;
//		for (int i = 0; i < diffur.getTPointsQuantity(); ++i) {
//			for (int j = 0; j < diffur.getHPointsQuantity(); ++j) {
//				parallelSolve[i][j] = Math.abs(parallelSolve[i][j] - serialSolve[i][j]);
//				System.out.print(String.format("%.7f\t", parallelSolve[i][j]));
//			}
//			System.out.println();
//		}
		System.out.println("\nВремя выполнения параллельного решения: " + executeTimeParallel + " нс");
	}

	private static double[][] calculateExactResult(Diffur diffur) {
		double trueMatrix[][] = new double[diffur.getTPointsQuantity()][diffur.getHPointsQuantity()];
		double t = diffur.getT0();
		for (int i = 0; i < diffur.getTPointsQuantity(); i++) {
			double x = diffur.getX0();
			for (int j = 0; j < diffur.getHPointsQuantity(); j++) {
				trueMatrix[i][j] = diffur.calculateTrueSolution(x, t);
				x += diffur.getH();
			}
			t += diffur.getTau();
		}
		System.out.println("\nТочный резульат:");
		diffur.printMatrix(trueMatrix);

		return trueMatrix;
	}

	private static void printResult(long executeTimeSerial, Diffur diffur, double[][] serialSolve, double[][] trueMatrix) {
		System.out.println("\nВремя выполнения последовательного решения: " + executeTimeSerial + " нс");
		System.out.println("Средняя абсолютная погрешность: " + calculateError(diffur, serialSolve, trueMatrix, 1));
		System.out.println("Максимальная абсолютная погрешность: " + calculateError(diffur, serialSolve, trueMatrix, 2));
		System.out.println("Средняя относительная погрешность: " + calculateError(diffur, serialSolve, trueMatrix, 3));
		System.out.println("Максимальная относительная погрешность: " + calculateError(diffur, serialSolve, trueMatrix, 4));
	}
	
	

	private static double calculateError(Diffur diffur, double[][] serialSolve, double[][] trueMatrix, int flag) {
		double[][] errorMatrix = new double[diffur.getTPointsQuantity()][diffur.getHPointsQuantity()];
		double error = 0.0;

		if (flag == 1 || flag == 2) {
			for (int i = 0; i < diffur.getTPointsQuantity(); i++) {
				for (int j = 0; j < diffur.getHPointsQuantity(); j++) {
					errorMatrix[i][j] = Math.abs(serialSolve[i][j] - trueMatrix[i][j]);
				}
			 }

			if (flag == 1) {
				for (int i = 0; i < diffur.getTPointsQuantity(); i++) {
					for (int j = 0; j < diffur.getHPointsQuantity(); j++) {
						error += errorMatrix[i][j];
					}
				}

				error = error / (diffur.getTPointsQuantity() * diffur.getHPointsQuantity());
			} else if (flag == 2) {
				error = errorMatrix[0][0];
				for (int i = 0; i < diffur.getTPointsQuantity(); i++) {
					for (int j = 0; j < diffur.getHPointsQuantity(); j++) {
						if (error < errorMatrix[i][j]) {
							error = errorMatrix[i][j];
						}
					}
				}
			}
		} else if (flag == 3 || flag == 4) {
			for (int i = 0; i < diffur.getTPointsQuantity(); i++) {
				for (int j = 0; j < diffur.getHPointsQuantity(); j++) {
					errorMatrix[i][j] = 100 * (Math.abs(serialSolve[i][j] - trueMatrix[i][j])) / trueMatrix[i][j];
				}
			}

			if (flag == 3) {
				for (int i = 0; i < diffur.getTPointsQuantity(); i++) {
					for (int j = 0; j < diffur.getHPointsQuantity(); j++) {
						error += errorMatrix[i][j];
					}
				}

				error = error / (diffur.getTPointsQuantity() * diffur.getHPointsQuantity());
			} else if (flag == 4) {
				error = errorMatrix[0][0];
				for (int i = 0; i < diffur.getTPointsQuantity(); i++) {
					for (int j = 0; j < diffur.getHPointsQuantity(); j++) {
						if (error < errorMatrix[i][j]) {
							error = errorMatrix[i][j];
						}
					}
				}
			}
		}
		return error;
	}

}