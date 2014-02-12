package com.cloudbees.groovy.cps;

import com.cloudbees.groovy.cps.impl.FunctionCallEnv;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Represents a CPS-transformed function.
 *
 * @author Kohsuke Kawaguchi
 */
public class CpsFunction extends CpsCallable {
    public CpsFunction(List<String> parameters, Block body) {
        super(parameters, body);
    }

    public Next invoke(Env caller, Object receiver, List<?> args, Continuation k) {
        Env e = new FunctionCallEnv(caller, receiver, k);
        assert args.size()== parameters.size();  // TODO: varargs

        for (int i=0; i< parameters.size(); i++) {
            e.setLocalVariable(parameters.get(i), args.get(i));
        }

        return new Next(body, e, k);
    }
}