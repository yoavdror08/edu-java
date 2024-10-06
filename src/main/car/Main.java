
public class Main {
	public static void main(String[] args) {
		CarInfo[] cars1 = new CarInfo[2];
		cars1[0] = new CarInfo("c10", true, 90);
		cars1[1] = new CarInfo("c11", false, 120);

		CarInfo[] cars2 = new CarInfo[2];
		cars2[0] = new CarInfo("c20", false, 45);
		cars2[1] = new CarInfo("c21", false, 50);

		CarInfo[] cars3 = new CarInfo[2];
		cars3[0] = new CarInfo("c30", false, 90);
		cars3[1] = new CarInfo("c31", false, 120);

		CameraInfo[] cameras = new CameraInfo[200];
		cameras[0] = new CameraInfo(12, 110, cars1);
		cameras[1] = new CameraInfo(12, 50, cars2);
		cameras[3] = new CameraInfo(11, 120, cars3);

		int good = legalCities(cameras);
	}
	
}