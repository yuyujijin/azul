JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        Square.java \
        Board.java \
        Bag.java \
        Tile.java \
        Test.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
        $(RM) *.class