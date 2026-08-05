package com.ajaxjs.net.ftp.sun.misc;

import java.io.PrintStream;

/**
 * A node in a regular expression finite state machine.
 */
public class RegexpNode {
    /**
     * Character represented by this node.
     */
    char c;

    /**
     * First child node in the tree.
     */
    RegexpNode firstchild;

    /**
     * Next sibling node.
     */
    RegexpNode nextsibling;

    /**
     * Depth of this node in the tree.
     */
    int depth;

    /**
     * True if this node represents an exact match.
     */
    boolean exact;

    /**
     * Result object associated with this node.
     */
    Object result;

    /**
     * Original regular expression string.
     */
    String re = null;

    /**
     * Creates a root node.
     */
    RegexpNode() {
        c = '#';
        depth = 0;
    }

    /**
     * Creates a node for the specified character and depth.
     *
     * @param C     character for this node
     * @param depth depth in the tree
     */
    RegexpNode(char C, int depth) {
        c = C;
        this.depth = depth;
    }

    /**
     * Adds a child node for the specified character.
     *
     * @param C character to add
     * @return the node for this character
     */
    RegexpNode add(char C) {
        RegexpNode p = firstchild;

        if (p == null)
            p = new RegexpNode(C, depth + 1);
        else {
            while (p != null) {
                if (p.c == C)
                    return p;
                else
                    p = p.nextsibling;
            }

            p = new RegexpNode(C, depth + 1);
            p.nextsibling = firstchild;
        }

        firstchild = p;

        return p;
    }

    /**
     * Finds a child node for the specified character.
     *
     * @param C character to find
     * @return the node for this character, or null if not found
     */
    RegexpNode find(char C) {
        for (RegexpNode p = firstchild; p != null; p = p.nextsibling) {
            if (p.c == C)
                return p;
        }

        return null;
    }

    /**
     * Prints this node and its children to the specified stream.
     *
     * @param out print stream to write to
     */
    void print(PrintStream out) {
        if (nextsibling != null) {
            RegexpNode p = this;
            out.print("(");

            while (p != null) {
                out.write(p.c);

                if (p.firstchild != null)
                    p.firstchild.print(out);

                p = p.nextsibling;
                out.write(p != null ? '|' : ')');
            }
        } else {
            out.write(c);

            if (firstchild != null)
                firstchild.print(out);
        }
    }
}
