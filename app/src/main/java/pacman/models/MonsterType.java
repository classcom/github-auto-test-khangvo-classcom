package pacman.models;

public enum MonsterType {
    Alien,
    Orion,
    Troll,
    TX5,
    Wizard;

    public String getImageName() {
        switch (this) {
            case Alien: return "m_alien.gif";
            case Orion: return "m_orion.gif";
            case Troll: return "m_troll.gif";
            case TX5: return "m_tx5.gif";
            case Wizard: return "m_wizard.gif";
            default: {
                assert false;
            }
        }
        return null;
    }
}
