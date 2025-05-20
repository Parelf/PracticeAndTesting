public class Main {

	public static void main(String[] args) throws InterruptedException {
		WeaponSystem weapon = new WeaponSystem();

		// Create 10 androids (threads) firing concurrently
		Thread[] androids = new Thread[10];
		for (int i = 0; i < androids.length; i++) {
			// to number threads (adroids)
			int androidNo = i;
			androids[i] = new Thread(() ->
			// Lambda start
			{
				for (int j = 0; j < 15; j++) {  // Each fires 15 times
					weapon.fire(androidNo);
					try {
						Thread.sleep(10); // Small delay to increase chance of overlap
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			// Lambda end
			}, "Android-" + i);
			androids[i].start();
		}

		// Wait for all androids to finish
		for (Thread t : androids) {
			t.join();
		}

		System.out.println("All firing missions complete.");
	}


}

class WeaponSystem {
	private int ammo = 100;

	public synchronized void fire(int androidNo) {
		if (ammo > 0) {
			System.out.println("Android %d firing weapon".formatted(androidNo));
			System.out.println("Ammo left: " + --ammo);
		}
	}
}
