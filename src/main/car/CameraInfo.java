
public class CameraInfo {
	private int city;
	private int maxSpeed;
	private CarInfo[] cars;	
	
	public CameraInfo(int city, int maxSpeed, CarInfo[] cars) {
		super();
		this.city = city;
		this.maxSpeed = maxSpeed;
		this.cars = cars;
	}
	
	public int getCity() {
		return city;
	}
	
	public int getMaxSpeed() {
		return maxSpeed;
	}	
	
}
