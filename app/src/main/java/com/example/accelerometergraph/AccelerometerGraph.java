package com.example.accelerometergraph;

import androidx.fragment.app.Fragment;

public abstract class  AccelerometerGraph  extends Fragment {

    abstract public void drawData(Cords data);

    static  class Cords {
        float x;
        float y;
        float z;

        public Cords(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getZ() {
            return z;
        }
    }
}
