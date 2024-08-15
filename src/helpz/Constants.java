package helpz;

public class Constants {
    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class Tiles{
        public static final int SKIN_TILE=0;
        public static final int TISSUE_TILE=1;
        public static final int VEIN_TILE=2;
    }

    public static class Enemies{
        public static final int FUNGI=0;
        public static final int BACTERIA=1;
        public static final int VIRUS=2;
        public static final int PARASITE=3;

        public static int GetGold(int EnemyType){
            switch (EnemyType){
                case FUNGI:
                    return 2;
                case BACTERIA:
                    return 5;
                case VIRUS:
                    return 7;
                case PARASITE:
                    return 10;
            }
            return 0;
        }
        public static float GetSpeed(int EnemyType){
            switch (EnemyType){
                case FUNGI:
                    return 0.5f;
                case BACTERIA:
                    return 0.6f;
                case VIRUS:
                    return 0.9f;
                case PARASITE:
                    return 0.5f;
            }
            return 0;
        }
        public static int GetStartHealth(int enemyType) {
            switch (enemyType){
                case FUNGI:
                    return 120;
                case BACTERIA:
                    return 150;
                case VIRUS:
                    return 100;
                case PARASITE:
                    return 250;
            }
            return 0;
        }
    }

    public static class Towers{
        public static final int WBC = 0;
        public static final int ANTISEPTIC = 1;
        public static final int VACCINE = 2;
        public static int getTowerCost(int towerType){
            switch (towerType){
                case WBC:
                    return 30;
                case ANTISEPTIC:
                    return 50;
                case VACCINE:
                    return 70;
                default:
                    return 0;
            }
        }
        public static String getName(int towerType){
            switch (towerType){
                case WBC:
                    return "WBC";
                case ANTISEPTIC:
                    return "Antiseptic";
                case VACCINE:
                    return "Vaccine";
                default:
                    return "";
            }
        }

        public static int getStartDmg(int towerType){
            switch (towerType){
                case WBC:
                    return 4;
                case ANTISEPTIC:
                    return 10;
                case VACCINE:
                    return 20;
                default:
                    return 0;
            }
        }

        public static float getStartRange(int towerType){
            switch (towerType){
                case WBC:
                    return 150;
                case ANTISEPTIC:
                    return 100;
                case VACCINE:
                    return 80;
                default:
                    return 0;
            }
        }

        public static float getStartCD(int towerType){
            switch (towerType){
                case WBC:
                    return 20;
                case ANTISEPTIC:
                    return 40;
                case VACCINE:
                    return 60;
                default:
                    return 0;
            }
        }
    }

    public static class Projectiles{
        public static final int EAT = 0;
        public static final int ALCOHOL = 1;
        public static final int ANTIBODIES = 2;

        public static float getSpeed(int Type){
            switch (Type){
                case EAT:
                    return 8f;
                case ALCOHOL:
                    return 6f;
                case ANTIBODIES:
                    return 4f;
            }
            return 0f;
        }
    }
}
