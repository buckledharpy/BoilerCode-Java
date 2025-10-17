import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class DemoViewer {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        JSlider headingSlider = new JSlider(0, 360, 180);
        pane.add(headingSlider, BorderLayout.SOUTH); // Positions slider south

        JSlider pitchSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);
        pane.add(pitchSlider, BorderLayout.EAST); // Positions slider east

        JPanel renderPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());

                List<Triangle> tris = new ArrayList<>();
                tris.add(new Triangle(new Vertex(100,100,100),
                        new Vertex(-100,-100,100),
                        new Vertex(-100,100,-100),
                        Color.WHITE));
                tris.add(new Triangle(new Vertex(100,100,100),
                        new Vertex(-100,-100,100),
                        new Vertex(100,-100,-100),
                        Color.RED));
                tris.add(new Triangle(new Vertex(-100,100,-100),
                        new Vertex(100,-100,-100),
                        new Vertex(100,100,100),
                        Color.GREEN));
                tris.add(new Triangle(new Vertex(-100,100,-100),
                        new Vertex(100,-100,-100),
                        new Vertex(-100,-100,100),
                        Color.BLUE));
                for(int i = 0 ; i < 4 ; i++){
                    tris = inflate(tris);
                }

                double heading = Math.toRadians(headingSlider.getValue());
                MatrixOf3 headingTransform = new MatrixOf3(new double[] {
                    Math.cos(heading), 0 , -Math.sin(heading),
                        0, 1, 0,
                        Math.sin(heading), 0 ,Math.cos(heading),
                });
                double pitch = Math.toRadians(pitchSlider.getValue());
                MatrixOf3 pitchTransform = new MatrixOf3(new double[] {
                   1, 0, 0,
                   0, Math.cos(pitch), Math.sin(pitch),
                   0, -Math.sin(pitch), Math.cos(pitch)
                });
                MatrixOf3 transform = headingTransform.multiply(pitchTransform);

                BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                double [] zBuffer = new double[img.getWidth() * img.getHeight()];
                for (int q = 0; q < zBuffer.length; q++) {
                    zBuffer[q] = Double.NEGATIVE_INFINITY;
                }

                for (Triangle t : tris) {
                    Vertex v1 = transform.transform(t.vertex1);
                    Vertex v2 = transform.transform(t.vertex2);
                    Vertex v3 = transform.transform(t.vertex3);
                    v1.x += getWidth() / 2;
                    v1.y += getHeight() / 2;
                    v2.x += getWidth() / 2;
                    v2.y += getHeight() / 2;
                    v3.x += getWidth() / 2;
                    v3.y += getHeight() / 2;

                    Vertex ab = new Vertex(v2.x - v1.x, v2.y - v1.y, v2.z - v1.z);
                    Vertex ac = new Vertex(v3.x - v1.x, v3.y - v1.y, v3.z - v1.z);
                    Vertex normal = new Vertex(
                      ab.y * ac.z - ab.z * ac.y,
                      ab.z * ac.x - ab.x * ac.z,
                      ab.x * ac.y - ab.y * ac.x
                    );
                    double normalLength = Math.sqrt(normal.x * normal.x + normal.y * normal.y + normal.z * normal.z);
                    normal.x /= normalLength;
                    normal.y /= normalLength;
                    normal.z /= normalLength;
                    double angleCos = Math.abs(normal.z);


                    int minX = (int) Math.max(0,Math.ceil(Math.min(v1.x, Math.min(v2.x, v3.x))));
                    int maxX = (int) Math.min(img.getWidth() -1, Math.floor(Math.max(v1.x, Math.max(v2.x, v3.x))));
                    int minY = (int) Math.max(0, Math.ceil(Math.min(v1.y, Math.min(v2.y, v3.y))));
                    int maxY = (int) Math.min(img.getHeight() -1, Math.floor(Math.max(v1.y, Math.max(v2.y, v3.y))));

                    double triangleArea = (v1.y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - v1.x);

                    for ( int y = minY; y <= maxY; y++ ) {
                        for ( int x = minX; x <= maxX; x++ ) {
                            double b1 = ((y-v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - x)) / triangleArea;
                            double b2 = ((y-v1.y) * (v3.x-v1.x) + (v3.y-v1.y) * (v1.x - x)) / triangleArea;
                            double b3 = ((y-v2.y) * (v1.x - v2.x) + (v1.y-v2.y) * (v2.x - x)) / triangleArea;
                            if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1) {
                                double depth = b1 * v1.z + b2 * v2.z + b3 * v3.z;
                                int zIdx = y * img.getWidth() + x;
                                if (zBuffer[zIdx] < depth) {
                                    img.setRGB(x,y, getShade(t.color, angleCos).getRGB());
                                    zBuffer[zIdx] = depth;
                                }
                            }
                        }
                    }

                }
                g2.drawImage(img, 0, 0, null);
            }
        };
        pane.add(renderPanel, BorderLayout.CENTER);
        frame.setSize(400, 400);
        frame.setVisible(true);
        headingSlider.addChangeListener(e -> renderPanel.repaint());
        pitchSlider.addChangeListener(e -> renderPanel.repaint());
    }
    public static Color getShade ( Color color , double shade) {
        double redLinear = Math.pow(color.getRed(), 2.4) * shade;
        double greenLinear = Math.pow(color.getGreen(), 2.4) * shade;
        double blueLinear = Math.pow(color.getBlue(), 2.4) * shade;
        int red = (int) Math.pow(redLinear, 1/2.4);
        int green = (int) Math.pow(greenLinear, 1/2.4);
        int blue = (int) Math.pow(blueLinear, 1/2.4);
        return new Color(red, green, blue);
    }
    public static List<Triangle> inflate(List<Triangle> tris) {
        List<Triangle> result = new ArrayList<>();
        for (Triangle t : tris) {
            Vertex m1 =
                    new Vertex((t.vertex1.x + t.vertex2.x)/2, (t.vertex1.y + t.vertex2.y)/2, (t.vertex1.z + t.vertex2.z)/2);
            Vertex m2 =
                    new Vertex((t.vertex2.x + t.vertex3.x)/2, (t.vertex2.y + t.vertex3.y)/2, (t.vertex2.z + t.vertex3.z)/2);
            Vertex m3 =
                    new Vertex((t.vertex1.x + t.vertex3.x)/2, (t.vertex1.y + t.vertex3.y)/2, (t.vertex1.z + t.vertex3.z)/2);
            result.add(new Triangle(t.vertex1, m1, m3, t.color));
            result.add(new Triangle(t.vertex2, m1, m2, t.color));
            result.add(new Triangle(t.vertex3, m2, m3, t.color));
            result.add(new Triangle(m1, m2, m3, t.color));
        }
        for (Triangle t : result) {
            for (Vertex v : new Vertex[]{t.vertex1, t.vertex2, t.vertex3}) {
                double l = Math.sqrt(v.x * v.x + v.y * v.y + v.z *v.z) / Math.sqrt(30000);
                v.x /= l;
                v.y /= l;
                v.z /= l;
            }
        }
        return result;
    }
}
class Vertex {
    double x, y, z;
    Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
class Triangle {
    Vertex vertex1, vertex2, vertex3;
    Color color;
    Triangle(Vertex vertex1, Vertex vertex2, Vertex vertex3, Color color) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.vertex3 = vertex3;
        this.color = color;
    }
}
class MatrixOf3 {
    double[] values;
    MatrixOf3(double[] values) {
        this.values = values;
    }
    MatrixOf3 multiply(MatrixOf3 other) {
        double[] result = new double[9];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                for (int i = 0; i < 3; i++) {
                    result[row * 3 + col]+=
                            this.values[row * 3 + i] * other.values[i * 3 + col];
                }
            }
        }
        return new MatrixOf3(result);
    }
    Vertex transform(Vertex in) {
        return new Vertex(
         in.x * values[0] + in.y * values[3] + in.z * values[6],
            in.x * values[1] + in.y * values[4] + in.z * values[7],
                in.x * values[2] + in.y * values[5] + in.z * values[8]
        );
    }
}