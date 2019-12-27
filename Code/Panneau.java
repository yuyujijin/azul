public class Panneau extends JPanel {


	public void paintComponent(Graphics g) {
		
		// factory

		g.drawOval(20, 20, 100, 100);
		g.drawOval(280, 20, 100, 100);
		g.drawOval(500, 20, 100, 100);
		g.drawOval(720, 20, 100, 100);
		g.drawOval(940, 20, 100, 100);
		
		//left board
		g.drawRoundRect(10, 200, 500, 400, 20, 20);
		
		//right board
		g.drawRoundRect(550, 200, 500, 400, 20, 20);

		
		// left patternLine
		g.drawRect(200, 400, 40, 40);
		

		g.drawRect(200, 360, 40, 40);
		g.drawRect(160, 400, 40, 40);

		g.drawRect(200, 320, 40, 40);
		g.drawRect(160, 360, 40, 40);
		g.drawRect(120, 400, 40, 40);

		g.drawRect(200, 280, 40, 40);
		g.drawRect(160, 320, 40, 40);
		g.drawRect(120, 360, 40, 40);
		g.drawRect(80, 400, 40, 40);

		g.drawRect(200, 240, 40, 40);
		g.drawRect(160, 280, 40, 40);
		g.drawRect(120, 320, 40, 40);
		g.drawRect(80, 360, 40, 40);
		g.drawRect(40, 400, 40, 40);

		// right patternLine

		g.drawRect(750, 400, 40, 40);

		g.drawRect(750, 360, 40, 40);
		g.drawRect(710, 400, 40, 40);

		g.drawRect(750, 320, 40, 40);
		g.drawRect(710, 360, 40, 40);
		g.drawRect(670, 400, 40, 40);

		g.drawRect(750, 280, 40, 40);
		g.drawRect(710, 320, 40, 40);
		g.drawRect(670, 360, 40, 40);
		g.drawRect(630, 400, 40, 40);

		g.drawRect(750, 240, 40, 40);
		g.drawRect(710, 280, 40, 40);
		g.drawRect(670, 320, 40, 40);
		g.drawRect(630, 360, 40, 40);
		g.drawRect(590, 400, 40, 40);

		// left discard

		g.drawRect(20, 500, 40, 40);
		g.drawRect(70, 500, 40, 40);
		g.drawRect(120, 500, 40, 40);
		g.drawRect(170, 500, 40, 40);
		g.drawRect(220, 500, 40, 40);
		g.drawRect(270, 500, 40, 40);
		g.drawRect(320, 500, 40, 40);

		// right discard
		g.drawRect(570, 500, 40, 40);
		g.drawRect(620, 500, 40, 40);
		g.drawRect(670, 500, 40, 40);
		g.drawRect(720, 500, 40, 40);
		g.drawRect(770, 500, 40, 40);
		g.drawRect(820, 500, 40, 40);
		g.drawRect(870, 500, 40, 40);

		// left wall
		g.setColor(Color.blue);
		g.drawRect(280, 240, 40, 40);
		g.setColor(Color.yellow);
		g.drawRect(322, 240, 40, 40);
		g.setColor(Color.red);
		g.drawRect(364, 240, 40, 40);
		g.setColor(Color.black);
		g.drawRect(406, 240, 40, 40);
		g.setColor(Color.pink);
		g.drawRect(448, 240, 40, 40);

		g.setColor(Color.pink);
		g.drawRect(280, 282, 40, 40);
		g.setColor(Color.blue);
		g.drawRect(322, 282, 40, 40);
		g.setColor(Color.yellow);
		g.drawRect(364, 282, 40, 40);
		g.setColor(Color.red);
		g.drawRect(406, 282, 40, 40);
		g.setColor(Color.black);
		g.drawRect(448, 282, 40, 40);

		g.setColor(Color.black);
		g.drawRect(280, 324, 40, 40);
		g.setColor(Color.pink);
		g.drawRect(322, 324, 40, 40);
		g.setColor(Color.blue);
		g.drawRect(364, 324, 40, 40);
		g.setColor(Color.yellow);
		g.drawRect(406, 324, 40, 40);
		g.setColor(Color.red);
		g.drawRect(448, 324, 40, 40);

		g.setColor(Color.red);
		g.drawRect(280, 366, 40, 40);
		g.setColor(Color.black);
		g.drawRect(322, 366, 40, 40);
		g.setColor(Color.pink);
		g.drawRect(364, 366, 40, 40);
		g.setColor(Color.blue);
		g.drawRect(406, 366, 40, 40);
		g.setColor(Color.yellow);
		g.drawRect(448, 366, 40, 40);

		g.setColor(Color.yellow);
		g.drawRect(280, 408, 40, 40);
		g.setColor(Color.red);
		g.drawRect(322, 408, 40, 40);
		g.setColor(Color.black);
		g.drawRect(364, 408, 40, 40);
		g.setColor(Color.pink);
		g.drawRect(406, 408, 40, 40);
		g.setColor(Color.blue);
		g.drawRect(448, 408, 40, 40);

		// right wall
		g.setColor(Color.blue);
		g.drawRect(830, 240, 40, 40);
		g.setColor(Color.yellow);
		g.drawRect(872, 240, 40, 40);
		g.setColor(Color.red);
		g.drawRect(914, 240, 40, 40);
		g.setColor(Color.black);
		g.drawRect(956, 240, 40, 40);
		g.setColor(Color.pink);
		g.drawRect(998, 240, 40, 40);

		g.setColor(Color.pink);
		g.drawRect(830, 281, 40, 40);
		g.setColor(Color.blue);
		g.drawRect(872, 281, 40, 40);
		g.setColor(Color.yellow);
		g.drawRect(914, 281, 40, 40);
		g.setColor(Color.red);
		g.drawRect(956, 281, 40, 40);
		g.setColor(Color.black);
		g.drawRect(998, 281, 40, 40);

		g.setColor(Color.black);
		g.drawRect(830, 322, 40, 40);
		g.setColor(Color.pink);
		g.drawRect(872, 322, 40, 40);
		g.setColor(Color.blue);
		g.drawRect(914, 322, 40, 40);
		g.setColor(Color.yellow);
		g.drawRect(956, 322, 40, 40);
		g.setColor(Color.red);
		g.drawRect(998, 322, 40, 40);

		g.setColor(Color.red);
		g.drawRect(830, 363, 40, 40);
		g.setColor(Color.black);
		g.drawRect(872, 363, 40, 40);
		g.setColor(Color.pink);
		g.drawRect(914, 363, 40, 40);
		g.setColor(Color.blue);
		g.drawRect(956, 363, 40, 40);
		g.setColor(Color.yellow);
		g.drawRect(998, 363, 40, 40);

		g.setColor(Color.yellow);
		g.drawRect(830, 404, 40, 40);
		g.setColor(Color.red);
		g.drawRect(872, 404, 40, 40);
		g.setColor(Color.black);
		g.drawRect(914, 404, 40, 40);
		g.setColor(Color.pink);
		g.drawRect(956, 404, 40, 40);
		g.setColor(Color.blue);
		g.drawRect(998, 404, 40, 40);
	}
