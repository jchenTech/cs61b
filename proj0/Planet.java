public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public static final double G = 6.67e-11;

	/** add in two Planet constructors that initialize an instance of Planet class*/
	public Planet(double xP, double yP, double xV,
              double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	public Planet(){
        this.xxPos = 0;
        this.yyPos = 0;
        this.xxVel = 0;
        this.yyVel = 0;
        this.mass = 0;
        this.imgFileName = "";
    }


	/**Calculate the distance between two planet */
	public double calcDistance(Planet target) {
		return Math.sqrt((target.xxPos - this.xxPos) * (target.xxPos - this.xxPos)
		 + (target.yyPos - this.yyPos) * (target.yyPos - this.yyPos));
	}

	/**Calculate the force exerted by the target planet */
	public double calcForceExertedBy(Planet target) {
		return G * target.mass * this.mass / (this.calcDistance(target) * this.calcDistance(target)); 
	}

	/**Calculate the force exerted by X and Y directions*/
	public double calcForceExertedByX(Planet target) {
		return this.calcForceExertedBy(target) * (target.xxPos - this.xxPos) / this.calcDistance(target);
	}

	public double calcForceExertedByY(Planet target) {
		return this.calcForceExertedBy(target) * (target.yyPos - this.yyPos) / this.calcDistance(target);
	}

	/**Calculate the net force exerted by X and Y directions */
	public double calcNetForceExertedByX(Planet[] allPlanets) {
		double NetForceX = 0.0;
		for (Planet p:allPlanets) {
			if (this.equals(p) == true) {
				continue;
			}else {
				NetForceX = NetForceX + this.calcForceExertedByX(p);
			}
		}
		return NetForceX;
	}

	public double calcNetForceExertedByY(Planet[] allPlanets) {
		double NetForceY = 0.0;
		for (Planet p:allPlanets) {
			if (this.equals(p) == true) {
				continue;
			}else {
				NetForceY = NetForceY + this.calcForceExertedByY(p);
			}
		}
		return NetForceY;
	}

	/**Update the planet's new volicity and position */
	public void update(double time, double forceX, double forceY) {
		double aX = forceX / this.mass;
		double aY = forceY / this.mass;

		this.xxVel = this.xxVel + aX * time;
		this.yyVel = this.yyVel + aY * time;

		this.xxPos = this.xxPos + this.xxVel * time;
		this.yyPos = this.yyPos + this.yyVel * time;
	}

	/**Draw the planet in the picture using StdDraw.picture() method*/
	public void draw() {
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
	}

}