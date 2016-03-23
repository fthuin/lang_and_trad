package packageOfClassToGenerate;

public class ClassToGenerate {
	public static int binomialCoefficient(int n, int k) {
		if ((k == 0) || (k == n))
			return 1;
		else
			return binomialCoefficient(n-1, k) + binomialCoefficient(n-1, k-1);
	}
}
