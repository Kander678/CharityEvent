package ser.mil.charityevent.domain.middleware.model;

public abstract class Middleware {
    private Middleware next;

    public static Middleware link(Middleware first, Middleware... chain) {
        Middleware head = first;
        for (Middleware nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract boolean check(ValidationContext ctx);

    protected boolean checkNext(ValidationContext ctx) {
        if (next == null) return true;
        return next.check(ctx);
    }
}
