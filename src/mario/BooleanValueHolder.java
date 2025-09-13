package mario;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

interface BooleanChangeListener {
    void booleanChanged(boolean newValue);
}
// 持有布林值的類
class BooleanValueHolder {
    private boolean value = false;
    private int scene = 1;
    private PropertyChangeSupport support;

    public BooleanValueHolder() {
        support = new PropertyChangeSupport(this);
    }

    // 添加監聽器
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    // 移除監聽器
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    // 改變布林值並通知監聽器
    public void setValue(boolean value) {
        boolean oldValue = this.value;
        this.value = value;
        support.firePropertyChange("value", oldValue, this.value);
    }

    // 獲取布林值
    public boolean getValue() {
        return value;
    }
    public void setScene(int scene) {
        this.scene = scene;
    }
    public int getScene() {
        return scene;
    }
    
}

