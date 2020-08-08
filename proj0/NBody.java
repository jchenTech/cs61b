public class NBody{
	/**Read the filepath and output the radius in the file */
	public static double readRadius(String filePath) {
		In in = new In(filePath);
		in.readDouble();
		Double radius = in.readDouble();
		return radius;
	}

	/**Read the file by the given filepath and return an array of Planets*/
	public static Planet[] readPlanets(String filePath) {
		In in = new In(filePath);
		int N = in.readInt();
		Planet[] planets = new Planet[N];
		in.readDouble();
		for (int j = 0; j < N; j++) {
			planets[j] = new Planet();
		}
		for (int i = 0; i < N; i++) {
			planets[i].xxPos = in.readDouble();
			planets[i].yyPos = in.readDouble();
			planets[i].xxVel = in.readDouble();
			planets[i].yyVel = in.readDouble();
			planets[i].mass = in.readDouble();
			planets[i].imgFileName = in.readString();
		}
		return planets;
	} 



	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];

		Planet[] planets = NBody.readPlanets(filename);
		double radius = NBody.readRadius(filename);

		String starField = "images/starfield.jpg";

		//This is a graphics technique to prevent flickering in the animation.
		StdDraw.enableDoubleBuffering();

		StdDraw.setScale(-radius, radius);
		StdDraw.clear();
		StdDraw.picture(0, 0, starField);

		for (Planet p : planets) {
			p.draw();
		}

		/**Creating the animation of the universe */
		double time = 0.0;
		while(time <= T) {
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];

			for (int i = 0; i < xForces.length; i++) {
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
			}

			for (int j = 0; j < yForces.length; j++) {
				yForces[j] = planets[j].calcNetForceExertedByY(planets);
			}

			for (int k = 0; k < planets.length; k++) {
				planets[k].update(dt, xForces[k], yForces[k]);
			}

			StdDraw.picture(0, 0, starField);
			for (Planet p : planets) {
				p.draw();
			}

			StdDraw.show();
			StdDraw.pause(10);

			time = time + dt;
		}

		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                  planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
}
	}
}