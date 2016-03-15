package g42116.rushhour.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author g42116
 */
public class CarTest {
    
/* Ajoutez des tests unitaires dans une classe CarTest permettant de vérifier 
adéquatement la méthode move, en ce compris le déclenchement de l’exception 

ainsi que la gestion correcte de la taille dans le constructeur
*/

//------------------------ Tests for the constructor ---------------------------
    
    @Test (expected=IllegalArgumentException.class)
    public void testConstructor1() {
        Car tested = new Car('a', 0, Orientation.HORIZONTAL, new Position(5,5));
    }
    
    @Test (expected=IllegalArgumentException.class)
    public void testConstructor2() {
        Car tested = new Car('g', 5, Orientation.VERTICAL, new Position(2,9));
    }
    
    @Test
    public void testMove() {
    }
    
//-------------------------- Tests for Car.getId() -----------------------------
    
    @Test
    public void testGetId1() {
        Car tested = new Car('r', 1, Orientation.HORIZONTAL, new Position(7,7));
        char expected = 'r';
        char result = tested.getId();
        assertEquals(expected, result);
    }
    
    @Test
    public void testGetId2() {
        Car tested = new Car('¬', 1, Orientation.VERTICAL, new Position(7,7));
        char expected = '¬';
        char result = tested.getId();
        assertEquals(expected, result);
    }    
    
    @Test
    public void testGetId3() {
        Car tested = new Car('6', 1, Orientation.HORIZONTAL, new Position(7,7));
        char expected = 'A';
        char result = tested.getId();
        assertFalse(expected == result);
    }
    

//---------------------- Tests for Car.getOrientation() ------------------------
    
    @Test
    public void testGetOrientation1() {
        Car tested = new Car('a', 2, Orientation.VERTICAL, new Position(2,2));
        Orientation expected = Orientation.VERTICAL;
        Orientation result = tested.getOrientation();
        assertEquals(expected, result);
    }
    
    @Test
    public void testGetOrientation2() {
        Car tested = new Car('a', 2, Orientation.HORIZONTAL, new Position(2,2));
        Orientation expected = Orientation.HORIZONTAL;
        Orientation result = tested.getOrientation();
        assertEquals(expected, result);
    }
    
    @Test
    public void testGetOrientation3() {
        Car tested = new Car('a', 2, Orientation.VERTICAL, new Position(2,2));
        Orientation expected = Orientation.HORIZONTAL;
        assertFalse(expected == tested.getOrientation());
    }
    
//----------------------- Tests for Car.getPosition() --------------------------
    
    @Test
    public void testGetPosition1() {
        Car tested = new Car('a', 3, Orientation.HORIZONTAL, new Position(0,3));
        Position expected = new Position(0,3);
        Position result = tested.getCurrentPosition();
        assertEquals(expected, result);
    }
    
    @Test
    public void testGetPosition2() {
        Car tested = new Car('a', 3, Orientation.VERTICAL, new Position(-5,0));
        Position expected = new Position(-5,0);
        Position result = tested.getCurrentPosition();
        assertEquals(expected, result);
    }
    
    @Test
    public void testGetPosition3() {
        Car tested = new Car('a', 3, Orientation.HORIZONTAL, new Position(4,4));
        Position expected = new Position(13,6);
        Position result = tested.getCurrentPosition();
        assertFalse(expected.equals(result));
    }

//---------------------- Tests for Car.move(direction) -------------------------
    
    @Test
    public void testMove1() {
        Car tested = new Car('a', 2, Orientation.VERTICAL, new Position(6,6));
        Position expected = new Position(7,6);
        tested.move(Direction.DOWN);
        Position result = tested.getCurrentPosition();
        assertEquals(expected, result);
    }
    
    @Test
    public void testMove2() {
        Car tested = new Car('a', 2, Orientation.VERTICAL, new Position(6,6));
        Position expected = new Position(5,6);
        tested.move(Direction.UP);
        Position result = tested.getCurrentPosition();
        assertEquals(expected, result);
    }
    
    @Test
    public void testMove3() {
        Car tested = new Car('a', 2, Orientation.HORIZONTAL, new Position(6,6));
        Position expected = new Position(6,5);
        tested.move(Direction.LEFT);
        Position result = tested.getCurrentPosition();
        assertEquals(expected, result);
    }
    
    @Test
    public void testMove4() {
        Car tested = new Car('a', 2, Orientation.HORIZONTAL, new Position(6,6));
        Position expected = new Position(6,7);
        tested.move(Direction.RIGHT);
        Position result = tested.getCurrentPosition();
        assertEquals(expected, result);
    }
    
    @Test (expected=IllegalArgumentException.class)
    public void testMove5() {
        Car tested = new Car('a', 2, Orientation.VERTICAL, new Position(6,6));
        tested.move(Direction.LEFT);
    }
    
    @Test (expected=IllegalArgumentException.class)
    public void testMove6() {
        Car tested = new Car('a', 2, Orientation.VERTICAL, new Position(6,6));
        tested.move(Direction.RIGHT);
    }
    
    @Test (expected=IllegalArgumentException.class)
    public void testMove7() {
        Car tested = new Car('a', 2, Orientation.HORIZONTAL, new Position(6,6));
        tested.move(Direction.DOWN);
    }
    
    @Test (expected=IllegalArgumentException.class)
    public void testMove8() {
        Car tested = new Car('a', 2, Orientation.HORIZONTAL, new Position(6,6));
        tested.move(Direction.UP);
    }
    
}
