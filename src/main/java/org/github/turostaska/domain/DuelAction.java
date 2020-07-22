package org.github.turostaska.domain;

public class DuelAction extends Action {
    private Player opponent;
    private boolean attackerWon;

    public DuelAction(int ID, long timeToFinishInSeconds, Player opponent) {
        super(ID, timeToFinishInSeconds);
        this.opponent = opponent;
    }

    @Override
    public void takeEffect(Player player) {
        //todo: szofisztikáltabb nyeremény?
        Player winner, loser;
        if (attackerWon) {
            winner = player; loser = opponent;
        }
        else {
            winner = opponent; loser = player;
        }
        winner.addMoney(loser.getMoney() / 2);
        loser.addMoney( -( loser.getMoney() / 2 ) );
    }

    @Override
    public boolean carryOutAndGetIfSuccessful(Player who) {
        //todo: szofisztikáltabb harcrendszer?
        int diceRoll = Util.getRandomInteger(0, who.getStrength() + opponent.getStrength() + 1);

        attackerWon = diceRoll <= who.getStrength();

        return true;
    }
}
