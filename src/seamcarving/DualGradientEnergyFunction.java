package seamcarving;

import edu.princeton.cs.algs4.Picture;

public class DualGradientEnergyFunction implements EnergyFunction {
    @Override
    public double apply(Picture picture, int x, int y) {
        if (!seamcarving.Utils.inBounds(x, y, picture.width(), picture.height())) {
            throw new IndexOutOfBoundsException();
        }
        double energy;
        double row;
        double col;
        if (x == 0) {
            row = Math.pow(-3 * picture.get(x, y).getRed() + 4 * picture.get(x + 1, y).getRed() -
                      picture.get(x + 2, y).getRed(), 2) +
                  Math.pow(-3 * picture.get(x, y).getGreen() + 4 * picture.get(x + 1, y).getGreen() -
                      picture.get(x + 2, y).getGreen(), 2) +
                  Math.pow(-3 * picture.get(x, y).getBlue() + 4 * picture.get(x + 1, y).getBlue() -
                      picture.get(x + 2, y).getBlue(), 2);
        } else if (x == picture.width() - 1) {
            row = Math.pow(-3 * picture.get(x, y).getRed() + 4 * picture.get(x - 1, y).getRed() -
                      picture.get(x - 2, y).getRed(), 2) +
                  Math.pow(-3 * picture.get(x, y).getGreen() + 4 * picture.get(x - 1, y).getGreen() -
                      picture.get(x - 2, y).getGreen(), 2) +
                  Math.pow(-3 * picture.get(x, y).getBlue() + 4 * picture.get(x - 1, y).getBlue() -
                      picture.get(x - 2, y).getBlue(), 2);
        } else {
            row = Math.pow(picture.get(x + 1, y).getRed() - picture.get(x - 1, y).getRed(), 2) +
                Math.pow(picture.get(x + 1, y).getGreen() - picture.get(x - 1, y).getGreen(), 2) +
                Math.pow(picture.get(x + 1, y).getBlue() - picture.get(x - 1, y).getBlue(), 2);
        }
        if (y == 0) {
            col = Math.pow(-3 * picture.get(x, y).getRed() + 4 * picture.get(x, y + 1).getRed() -
                      picture.get(x, y + 2).getRed(), 2) +
                  Math.pow(-3 * picture.get(x, y).getGreen() + 4 * picture.get(x, y + 1).getGreen() -
                      picture.get(x, y + 2).getGreen(), 2) +
                  Math.pow(-3 * picture.get(x, y).getBlue() + 4 * picture.get(x, y + 1).getBlue() -
                      picture.get(x, y + 2).getBlue(), 2);
        } else if (y == picture.height() - 1) {
            col = Math.pow(-3 * picture.get(x, y).getRed() + 4 * picture.get(x, y - 1).getRed() -
                     picture.get(x, y - 2).getRed(), 2) +
                  Math.pow(-3 * picture.get(x, y).getGreen() + 4 * picture.get(x, y - 1).getGreen() -
                      picture.get(x, y - 2).getGreen(), 2) +
                  Math.pow(-3 * picture.get(x, y).getBlue() + 4 * picture.get(x, y - 1).getBlue() -
                      picture.get(x, y - 2).getBlue(), 2);
        } else {
            col = Math.pow(picture.get(x, y + 1).getRed() - picture.get(x, y - 1).getRed(), 2) +
                Math.pow(picture.get(x, y + 1).getGreen() - picture.get(x, y - 1).getGreen(), 2) +
                Math.pow(picture.get(x, y + 1).getBlue() - picture.get(x, y - 1).getBlue(), 2);
        }
        energy = Math.sqrt(row + col);
        return energy;
    }
}
