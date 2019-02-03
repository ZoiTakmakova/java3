package ru.zt.idsys.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Clients extends ForwardingSet<CLientData>{

    private Set<CLientData> delegate;
    public Clients(Clients clients){this.delegate = new HashSet<CLientData>(clients.delegate);}
    public Clients() {this.delegate = new HashSet<CLientData>();}
    public Clients(Collection<CLientData> clients){this.delegate = new HashSet<CLientData>(clients); }

    @Override
    protected Set<CLientData> delegate() {
        return delegate;
    }
    public Clients withAdded(CLientData client){
        Clients clients = new Clients(this);
        clients.add(client);
        return clients;
    }

    public Clients without(CLientData client){
        Clients clients = new Clients(this);
        clients.remove(client);
        return clients;
    }

}
