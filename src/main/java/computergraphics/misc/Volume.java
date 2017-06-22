package computergraphics.misc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Volume {

	private Path path = Paths.get("C:\\Users\\floha\\semester4workspace\\java\\assets\\volumedata\\neghip.vox");
	public byte[] f;
	public int rX;
	public int rY;
	public int rZ;
	public byte[][] rary = new byte[rX][rY * rZ * 4];

	public void readVolumeData(Path path) throws IOException {
		this.f = Files.readAllBytes(path);
	}

	public byte accessVolumeData(int x, int y, int z) {
		return this.f[z * rX * rY + y * rX + x];
	}

	public byte[][] getTextureStack(String axis) {
		switch (axis) {
		case "x": {
			byte[][] rary = new byte[rX][rY * rZ * 4];
			int count = 0;

			for (int n = 0; n < rX; n++) {
				for (int i = 0; i < rY; i++) {
					for (int j = 0; j < rZ; j++) {
						rary[n][count] = accessVolumeData(n, i, j);
						count++;
						rary[n][count] = 0;
						count++;
						rary[n][count] = 0;
						count++;
						rary[n][count] = accessVolumeData(n, i, j);
						count++;
					}
				}
				count = 0;
			}
			return rary;
		}
		}
		return null;
	}

	public void calcTextureStacks() {
		
		int count = 0;

		for (int n = 0; n < rX; n++) {
			for (int i = 0; i < rY; i++) {
				for (int j = 0; j < rZ; j++) {
					rary[n][count] = accessVolumeData(n, i, j);
					count++;
					rary[n][count] = 0;
					count++;
					rary[n][count] = 0;
					count++;
					rary[n][count] = accessVolumeData(n, i, j);
					count++;
				}
			}
			count = 0;
		}

		for (int n = 0; n < rZ; n++) {
			for (int i = 0; i < rX; i++) {
				for (int j = 0; j < rY; j++) {
					rary[n][count] = accessVolumeData(i, j, n);
					count++;
					rary[n][count] = 0;
					count++;
					rary[n][count] = 0;
					count++;
					rary[n][count] = accessVolumeData(i, j, n);
					count++;
				}
			}
			count = 0;
		}
	}

	public Volume(Path path, int resx, int resy, int resz) throws IOException {
		readVolumeData(path);
		this.rX = resx;
		this.rY = resy;
		this.rZ = resz;
	}

	public static void main(String[] args) {
	}

}
