import java.io.*;
import java.util.List;

public class YoRHaUnit implements Serializable {

	private static final long serialVersionUID = 1L;

	// Custom version number for manual versioning
	private static final int VERSION = 2;

	private String modelName;
	private int level;
	private List<String> equippedWeapons;
	private transient String secretCode;  // transient: won't be serialized

	// New field added in version 2 (simulate future version)
	private String specialAbility;

	// V1
	public YoRHaUnit(String modelName, int level, List<String> equippedWeapons, String secretCode) {
		this.modelName = modelName;
		this.level = level;
		this.equippedWeapons = equippedWeapons;
		this.secretCode = secretCode;
	}

	// V2
	public YoRHaUnit(String modelName, int level, List<String> equippedWeapons, String secretCode, String specialAbility) {
		this.modelName = modelName;
		this.level = level;
		this.equippedWeapons = equippedWeapons;
		this.secretCode = secretCode;
		this.specialAbility = specialAbility;
	}

	@Override
	public String toString() {

		return "YoRHaUnit{" +
				"modelName='" + modelName + '\'' +
				", level=" + level +
				", equippedWeapons=" + equippedWeapons +
				", secretCode='" + secretCode + '\'' +
				", specialAbility='" + specialAbility + '\'' +
				'}';

	}

	/**
	 * Custom writeObject for versioning and control.
	 */
	@Serial
	private void writeObject(ObjectOutputStream out) throws IOException {
//		out.writeInt(VERSION); // Write version first
//		out.defaultWriteObject(); // Write non-transient fields automatically
//		// Manually write transient fields if desired
//		out.writeUTF(secretCode != null ? secretCode : "");
		out.writeInt(VERSION);
		out.defaultWriteObject();
		out.writeUTF(secretCode != null ? secretCode : "");
		out.writeUTF(specialAbility != null ? specialAbility : "");

	}

	/**
	 * Custom readObject for versioning and defensive reading.
	 */
	@Serial
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
//		int version = in.readInt(); // Read version first
//
//		// Read default fields
//		in.defaultReadObject();
//
//		// Read transient field manually
//		this.secretCode = in.readUTF();
//		if (secretCode.isEmpty()) {
//			secretCode = null;
//		}
//
//		// Defensive versioning: handle future versions here
//		if (version > VERSION) {
//			// For example, skip or read new fields added in future versions
//			// e.g., if (version >= 2) { specialAbility = in.readUTF(); }
//			System.out.println("Warning: Reading newer version " + version + ", some fields may be missing.");
//		}

		int version = in.readInt();
		in.defaultReadObject();
		this.secretCode = in.readUTF();
		if (version >= 2) {
			this.specialAbility = in.readUTF();
		} else {
			this.specialAbility = "None"; // default for old versions
		}

	}

}
