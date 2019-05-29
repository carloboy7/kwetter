package dbal.context;

import entities.kweet.Kweet;
import entities.user.User;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Default;

@Alternative
@ApplicationScoped
public class KweetMemoryContext extends SimpleMemoryContext<Kweet> implements KweetContext {
}
