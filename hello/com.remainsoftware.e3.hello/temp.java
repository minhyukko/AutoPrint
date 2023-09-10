    public IntListVer3(){
        //redirect to single int constructor
        this(DEFAULT_CAP);
        //other statments could go here.
    }

    /**
     * Constructor to allow user of class to specify
     * initial capacity in case they intend to add a lot
     * of elements to new list. Creates an empty list.
     * @param initialCap > 0
     */
    public IntListVer3(int initialCap) {
        assert initialCap > 0 : "Violation of precondition. IntListVer1(int initialCap):"
                + "initialCap must be greater than 0. Value of initialCap: " + initialCap;
        iValues = new int[initialCap];
        iSize = 0;
    }

    /**
     * Default add method. Add x to the end of this IntList.
     * Size of the list goes up by 1.
     * @param x The value to add to the end of this list.
     */
    public void add(int x){
        //example of loose coupling
        insert(iSize, x);
    }

    /**
     * Retrieve an element from the list based on position.
     * @param pos 0 <= pos < size()
     * @return The element at the given position.
     */
    public int get(int pos){
        assert 0 <= pos && pos < size() : "Failed precondition get. " +
                "pos it out of bounds. Value of pos: " + pos;
        return iValues[pos];
    }

    /**
     * Insert x at position pos. Elements with a position equal
     * to pos or more are shifted to the right. (One added to their
     * position.)
     * post: get(pos) = x, size() = old size() + 1
     * @param pos 0 <= pos <= size()
     * @param x
     */
    public void insert(int pos, int x){
        assert 0 <= pos && pos <= size() : "Failed precondition insert. " +
                "pos is invalid. Value of pos: " + pos;
        ensureCapcity();
        for(int i = iSize; i > pos; i--){
            iValues[i] = iValues[i - 1];
        }
        iValues[pos] = x;
        iSize++;
    }

    /**
     * Remove an element from the list based on position.
     * Elements with a position greater than pos
     * are shifted to the left. (One subtracted from their
     * position.)
     * @param pos 0 <= pos < size()
     * @return The element that is removed.
     */
    public int remove(int pos){
        assert 0 <= pos && pos < size() : "Failed precondition remove. " +
                "pos it out of bounds. Value of pos: " + pos;
        int removedValue = iValues[pos];
        for(int i = pos; i < iSize - 1; i++)
            iValues[i] = iValues[i + 1];
        iSize--;
        return removedValue;
    }

    private void ensureCapcity(){
        // is there extra capacity available?
        // if not, resize
        if(iSize == iValues.length)
            resize();
    }
