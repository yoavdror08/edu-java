
public class Main {
	public static void main(String[] args) {
		CarInfo[] cars1 = new CarInfo[2];
		cars1[0] = new CarInfo("c10", true, 90);
		cars1[1] = new CarInfo("c11", false, 120);

		CarInfo[] cars2 = new CarInfo[2];
		cars2[0] = new CarInfo("c20", false, 45);
		cars2[1] = new CarInfo("c21", false, 50);

		CameraInfo[] cameras = new CameraInfo[200];
		cameras[0] = new CameraInfo(12, 110, cars1);
		cameras[1] = new CameraInfo(12, 50, cars2);

		int good = legalCities(cameras);
	}
	
}