A simple scala app, which reads photos from a directory given in the resource (.conf) and assess if photo
is bright or dark (0-100%) and saves those pictures in another directory
with extended file name by _white or _dark along with their brightness percentage.

Example:
When we run the script given an input folder “in”, output folder “out” and configured 
“cut off“ point of 55, then the final result should be this directory structure: in/ 
perfectly_white.png perfectly_black.jpg colorful_image.jpg out/ perfectly_white_bright_0. png
perfectly_black_dark_100. jpg colorful_image_dark_55.jpg

Grayscale conversion is done and then average grayscale value is taken for classifing bright or 
dark image accordingly. Pureconfig process is used for accessing the resource.

