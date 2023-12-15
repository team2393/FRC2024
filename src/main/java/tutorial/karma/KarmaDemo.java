package tutorial.karma;

public class KarmaDemo
{
    public static void main(String[] args)
    {
        Karma fred = new Karma();
        Karma jane = new GirlsKarma();
        
        fred.do_something_good();
        fred.do_something_good();
        fred.do_something_good();
        fred.do_something_good();
        fred.do_something_good();
        System.out.println("Fred has " + fred.getPoints() + " points, which equals $" + fred.get_money() );

        jane.do_something_good();
        jane.do_something_good();
        jane.do_something_good();
        jane.do_something_good();
        jane.do_something_good();
        System.out.println("Jane has " + jane.getPoints() + " points, which equals $" + jane.get_money() );

        fred.do_something_bad();
        System.out.println("Fred has " + fred.getPoints() + " points, which equals $" + fred.get_money() );


        jane.do_something_bad();
        System.out.println("Jane has " + jane.getPoints() + " points, which equals $" + jane.get_money() );
    }
}
