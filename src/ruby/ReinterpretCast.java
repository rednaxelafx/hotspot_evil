$: << File.dirname(__FILE__)
require '../../lib/hotspot_evil'

import java.lang.UnsupportedOperationException

builder = FileBuilder.build(__FILE__) do
  package 'hotspot.evil' do
    public_class 'ReinterpretCast', MagicAccessorImpl do
      public_static_method :toBoolean, [], boolean, int do
        iload 0
        ireturn
      end
      
      public_static_method :toInt, [], int, boolean do
        iload 0
        ireturn
      end
      
      public_static_method :toInt, [], int, float do
        iload 0
        ireturn
      end
      
      public_static_method :toLong, [], long, double do
        lload 0
        lreturn
      end
      
      public_static_method :toFloat, [], float, int do
        fload 0
        freturn
      end
      
      public_static_method :toDouble, [], double, long do
        dload 0
        dreturn
      end
      
      public_static_method :toPlatformEndianInt, [], int, byte[] do
        not_supported = label
        
        aload 0
        arraylength
        push_int 4
        if_icmplt not_supported
        
        aload 0
        getfield java.awt.Point, :y, int
        ireturn
        
        not_supported.set!
        new UnsupportedOperationException
        dup
        ldc "doesn't support byte array of length less than 4"
        invokespecial UnsupportedOperationException, :'<init>', [void, string]
        athrow
      end
    end
  end
end

outdir = ARGV.first || '.'

builder.generate do |filename, class_builder|
  require 'fileutils'
  FileUtils.mkdir_p(File.join(outdir, File.dirname(filename)))
  File.open(File.join(outdir, filename), 'wb') do |file|
    file.write(class_builder.generate)
  end
end

