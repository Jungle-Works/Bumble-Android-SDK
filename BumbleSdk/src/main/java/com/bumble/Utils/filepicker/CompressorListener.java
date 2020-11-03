package com.bumble.Utils.filepicker;


import com.bumble.Utils.filepicker.filter.entity.ImageFile;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by gurmail on 11/01/19.
 *
 * @author gurmail
 */
public interface CompressorListener {
    void onImageCompressed(File file, String path, ImageFile imageFile, ArrayList<Integer> integers);
}
